(defproject socket-chat "0.1.0"
  :description "Just a socket chat"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]]
  :main ^:skip-aot socket-chat.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
