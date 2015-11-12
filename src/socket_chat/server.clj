(ns socket-chat.server
  (require '[clojure.java.io :as io]
           '[socket-chat.common :as common])
  (import '[java.net ServerSocket]))

(defn serve [port handler]
  (with-open [server-sock (ServerSocket. port)
              sock (.accept server-sock)]
    (let [msg-in (common/receive-msg sock)
          msg-out (handler msg-in)]
      (common/send-msg sock msg-out))))
