(ns socket-chat.common
  (:require [clojure.java.io :as io]))

(defn receive-msg
  "Read a line of textual data from the given socket"
  [socket]
  (.readLine (io/reader socket)))

(defn send-msg
  "Send the given string message out over the given socket"
  [socket msg]
  (let [writer (io/writer socket)]
    (.write writer msg)
    (.flush writer)))