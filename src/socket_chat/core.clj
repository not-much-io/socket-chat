(ns socket-chat.core
  ()
  (:gen-class))

(defn -main [& args]
  (if (not= (count args) 1)
    (println "Number of arguments")
    (comment "Add code here")))