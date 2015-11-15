(ns socket-chat.client
  (:import [java.net Socket])
  (:require [socket-chat.common :refer [send-msg receive-msg]]))

(def user-profile (atom {}))

(defn- build-msg [msg]
  (str (:handle-name @user-profile) "|" msg))

(defn- prompt-for-input
  [prompt]
  (print prompt)
  (flush))

(defn- get-input
  []
  (prompt-for-input "$ ")
  (str (read-line) "\n"))

(defn- get-handle-name
  []
  (prompt-for-input "Enter a handle name: ")
  (str (read-line)))

(defn- message-receiver-loop
  [connection]
  (println "Starting message receiving loop.")
  (loop [in-msg (receive-msg connection)]
    (println in-msg)
    (recur (receive-msg connection))))

(defn- start-listener-thread
  [connection]
  (println "Starting listener thread.")
  (doto
    (Thread. #(message-receiver-loop connection))
    (.setDaemon true)
    (.start)))

(defn- start-client
  [ip port]
  (println "Running client..")
  (with-open [server-socket (Socket. ip port)]
    (send-msg server-socket (str (:handle-name @user-profile) "\n"))
    (start-listener-thread server-socket)
    (loop [msg (get-input)]
      (send-msg server-socket (build-msg msg))
      (recur (get-input)))))

(defn handle-starting-client
  [opts]
  (let [ip      (:ip opts)
        port    (:port opts)
        handle  (get-handle-name)]
    (swap! user-profile assoc :handle-name handle)
    (start-client ip port)))