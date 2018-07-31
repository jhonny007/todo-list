(defproject todo-list "0.1.0-SNAPSHOT"
  :description "A simple webapp using ring"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.4.0"]
                 [hiccup "1.0.5"]
                 [compojure "1.5.1"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [postgresql/postgresql "9.1-901-1.jdbc4"]]
  :main todo-list.core
  :min-lein-version "2.0.0"
  :uberjar-name "todo-list.jar"
  :auto-clean false
  :profiles {:dev
             {:main todo-list.core/-dev-main}})
