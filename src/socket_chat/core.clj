(ns socket-chat.core
  (:require [socket-chat.server :refer [handle-starting-server]]
            [socket-chat.client :refer [handle-starting-client]]
            [clojure.tools.cli  :refer [parse-opts]])
  (:gen-class))

(defn in?
  "true if seq contains elm"
  [seq elm]
  (some #(= elm %) seq))

(def cli-options [["-m" "--mode MODE" "Server/Client"
                   :default "server"
                   :validate [#(in? #{"client" "server"}
                                    (clojure.string/lower-case %))
                              "Mode must be either client or server"]]
                  ["-i" "--ip" "IP address of server"
                   :default "localhost"]
                  ["-p" "--port"
                   :default 8989
                   :parse-fn #(Integer. %)]])

(defn usage
  []
  (println " -m or --mode is for MODE either server or client (default server)\n"
           "-i or --ip   is for the IP address of the server (default hostname)\n"
           "-p or --port is for the PORT to be connected to  (default 8989)\n"
           "ex. java -jar socket-chat.jar -m client -i 85.253.154.169 -p 8989\n"))

(defn -main
  [& args]
  (if (seq? args)
    (let [args-map (parse-opts args cli-options)
          opts     (:options args-map)
          errors   (:errors args-map)
          _        (if (seq? errors)
                     (println errors))
          mode     (:mode opts)]
      (cond (= mode "server") (handle-starting-server opts)
            (= mode "client") (handle-starting-client opts)))
    (usage)))

