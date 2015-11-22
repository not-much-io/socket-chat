(ns socket-chat.core
  (:require [socket-chat.server :refer [handle-starting-server]]
            [socket-chat.client :refer [handle-starting-client]]
            [socket-chat.socket-chat-util :refer [in?]])
  (:gen-class))


(defn usage
  []
  (println " -m is for MODE either server or client\n"
           "-i is for the IP address of the server\n"
           "-p is for the PORT to be connected to\n"
           "ex. java -jar socket-chat.jar -m client -i 85.253.154.169 -p 8989\n\n"))

(defn- valid-client-params? [args-map]
  (let [port (:-p args-map)
        ip   (:-i args-map)]
    (not (or (nil? port)
             (nil? ip)))))

(defn- valid-server-params? [args-map]
  (let [port (:-p args-map)]
    (not (nil? port))))

(defn- valid-args? [args-map]
  (let [mode (:-m args-map)]
    (cond (= mode "server") (valid-server-params? args-map)
          (= mode "client") (valid-client-params? args-map)
          :else false)))

(defn- parse-args [args]
  (if (not-empty args)
    (let [args-part (partition 2 args)
          args-map  (loop [args-map {}
                           args args-part]
                      (if (empty? args)
                        args-map
                        (let [pair (last args)]
                          (recur (assoc args-map (keyword (first pair))
                                                 (second pair))
                                 (drop-last args)))))]
      (if (valid-args? args-map)
        args-map
        (do (println "Invalid arguments.")
            nil)))
    nil))

(defn -main
  [& args]
  (let [args-map (parse-args args)]
    (if (nil? args-map)
      (usage)
      (let [_     (println args-map)
            mode (:-m args-map)]
        (cond (= mode "server") (handle-starting-server args-map)
              (= mode "client") (handle-starting-client args-map)
              :else (println "Invalid mode, must be server of client."))))))

