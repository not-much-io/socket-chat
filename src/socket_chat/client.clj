(ns socket-chat.client
  (:import [java.net Socket])
  (:require [socket-chat.common :refer [send-msg receive-msg]]))

(defn get-input
  []
  (print "$ ")
  (str (read-line) (newline)))

(defn start-client
  [ip port]
  (println "Running client..")
  (with-open [client-socket (Socket. ip port)]
    (loop [msg (get-input)]
      (send-msg client-socket msg)
      (recur (get-input)))))

(defn handle-starting-client
  [opts]
  (let [ip    (:ip opts)
        port  (:port opts)]
    (start-client ip port)))