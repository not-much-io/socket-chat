(ns socket-chat.server
  (:require [socket-chat.common :refer [send-msg receive-msg]]
            [socket-chat.socket-chat-util :refer [keep-thread-alive]])
  (:import [java.net ServerSocket]))

(def connections (atom []))

(defn- message-handler
  [msg]
  (let [sender (first (clojure.string/split msg #" says: "))
        conns  (filter #(not= sender (:handle-name %)) @connections)]
    (doall (for [conn conns]
             (send-msg (:new-conn conn) (str msg "\n"))))))

(defn- message-receiver-loop
  [connection]
  (println "Starting message receiving loop on connection: " (:handle-name connection))
  (let [handle-name (:handle-name connection)
        socket      (:new-conn connection)]
    (loop [in-msg (receive-msg socket)]
      (message-handler in-msg)
      (recur (receive-msg socket)))))

(defn- start-listener-thread [connection]
  (println "Starting listener thread on connecton: " (:handle-name connection))
  (doto
    (Thread. #(message-receiver-loop connection))
    (.setDaemon true)
    (.start)))

(defn- accept-connection
  "If a new connection is made to the server, it is accepted and the handle name collected."
  [server-socket]
  (println "Waiting for next connection to accept..")
  (let [new-conn    (.accept server-socket)
        handle-name (receive-msg new-conn)]
    {:new-conn    new-conn
     :handle-name handle-name}))

(defn- accept-connections-loop
  "Endless loop to accept connections and place them into the connections atom - handle-name : connection object"
  [server-socket]
  (println "Starting connection accepting loop..")
  (loop [conn (accept-connection server-socket)]
    (start-listener-thread conn)
    (swap! connections conj conn)
    (recur (accept-connection server-socket))))

(defn- start-connection-accepting-thread
  "Start a thread that will run the connection accepting loop in the background. Is a daemon thread so it would close together with the server"
  [server-socket]
  (println "Started accepting connections..")
  (doto
    (Thread. #(accept-connections-loop server-socket))
    (.setDaemon true)
    (.start)))

(defn- start-server
  [port]
  (println "Running server..")
  (with-open [server-sock (ServerSocket. port)]
    (start-connection-accepting-thread server-sock)
    (keep-thread-alive)))

(defn handle-starting-server
  [opts]
  (let [port (Integer/parseInt (:-p opts))]
    (start-server port)))