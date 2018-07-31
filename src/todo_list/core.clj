(ns todo-list.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [routes]]
            [hiccup.core :refer :all]
            [hiccup.page :refer :all]
            [todo-list.handlers :refer [base]]
            [todo-list.worms :refer [myname]] ))



(def app
  (routes myname base))

(defn -main
  "A very simple web server using Ring & Jetty"
  [port-number]
  (jetty/run-jetty app
   {:port (Integer. port-number)}))

(defn -dev-main
  "A very simple web server using Ring & Jetty & reloads code changes"
  [port-number]
  (jetty/run-jetty (wrap-reload #'app) 
   {:port (Integer. port-number)}))

(defn remote-heroku-db-spec [host port database username password]
  {:connection-uri (str "jdbc:postgresql://" host ":" port "/" database "?user=" username "&password=" password "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory")})
