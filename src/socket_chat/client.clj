(ns socket-chat.client
  (:import [java.net Socket])
  (:require [socket-chat.common :refer [send-msg receive-msg]]))

(defn get-input
  []
  (println "Enter a message to send: ")
  (str (read-line) "\n"))

(defn start-client
  [ip port]
  (println "Running client..")
  (with-open [client-socket (Socket. ip port)]
    (loop [msg (get-input)]
      (send-msg client-socket msg)
      (recur (get-input)))))