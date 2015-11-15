(ns socket-chat.socket-chat-util)

(defn keep-thread-alive
  []
  (while true
    (Thread/sleep 100000)))

(defn in?
  "true if seq contains elm"
  [seq elm]
  (some #(= elm %) seq))