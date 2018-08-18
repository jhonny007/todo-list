(defproject todo-list "0.1.0-SNAPSHOT"
  :description "A simple webapp using ring"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ring "1.4.0"]
                 [hiccup "1.0.5"]
                 [hiccup-bootstrap "0.1.2"]
                 [compojure "1.5.2"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [korma "0.4.0"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [com.h2database/h2 "1.3.170"]]
  :main todo-list.core
  :resource-paths ["resources"]
  :min-lein-version "2.0.0"
  :uberjar-name "todo-list.jar"
  :auto-clean false
  :profiles {:dev
             {:main todo-list.core/-dev-main}}
  )
