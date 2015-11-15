(ns socket-chat.client
  (:import [java.net Socket])
  (:require [socket-chat.common :refer [send-msg receive-msg]]))

(def user-profile (atom {}))

(defn build-msg [msg]
  (str (:handle-name user-profile) msg))

(defn prompt-for-input
  [prompt]
  (print prompt)
  (flush))

(defn get-input
  []
  (prompt-for-input "$ ")
  (str (read-line) "\n"))

(defn get-handle-name
  []
  (prompt-for-input "Enter a handle name: ")
  (str (read-line) "\n"))

(defn start-client
  [ip port]
  (println "Running client..")
  (with-open [client-socket (Socket. ip port)]
    (send-msg client-socket (:handle-name user-profile))
    (loop [msg (get-input)]
      (send-msg client-socket (build-msg msg))
      (recur (get-input)))))

(defn handle-starting-client
  [opts]
  (let [ip      (:ip opts)
        port    (:port opts)
        handle  (get-handle-name)]
    (swap! user-profile assoc :handle-name handle)
    (start-client ip port)))