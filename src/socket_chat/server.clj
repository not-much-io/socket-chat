(ns socket-chat.server
  (:require [socket-chat.common :refer [send-msg receive-msg]])
  (:import [java.net ServerSocket]))

(defn message-handler
  [msg]
  (println msg))

(defn start-server
  [port]
  (println "Running server..")
  (with-open [server-sock (ServerSocket. port)
              sock        (.accept server-sock)]
    (loop [msg-in  (receive-msg sock)]
      (message-handler msg-in)
      (recur (receive-msg sock)))))
