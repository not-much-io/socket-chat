(ns socket-chat.core
  (:require [socket-chat.server :refer (start-server)]
            [socket-chat.client :refer (start-client)])
  (:gen-class))

(defn handle-starting-client
  [args]
  (let [ip (first args)
        port (read-string (second args))]
    (start-client ip port)))

(defn handle-starting-server
  [args]
  (let [port (read-string (first args))]
    (start-server port)))

(defn show-help
  []
  (println "showing help"))

(defn -main
  [& args]
  (if (seq? args)
    (let [mode (first args)
          args (rest args)]
      (cond (= mode "server") (handle-starting-server args)
            (= mode "client") (handle-starting-client args)
            :else (do (println "Invalid mode argument")
                      (show-help))))
    (show-help)))

