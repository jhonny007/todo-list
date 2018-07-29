(ns todo-list.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]
))

(def operands {"+" + "-" - "*" * ":" /})

;worm data
(defstruct owner :name :wormbins)
(defstruct wormbin :name :feedings)
(defstruct feeding :date :time :weight :section :picture)

(def feeding1 (struct feeding "29.07.2018" "10:00" 200 "Q1" ))
(def feeding2 (struct feeding "29.07.2018" "12:00" 175 "Q2" ))
(def feeding3 (struct feeding "30.07.2018" "08:00" 250 "Q3" ))
(def wb1 (struct wormbin "Hungry Bin" [feeding1 feeding2 feeding3] ))
(def chris (struct owner "Christian" [wb1]))


(defn bins
  [request]
  {:status 200
   :body (str wb1) })

(defn owners
  [request]
  {:status 200
   :body (str chris) })
;----------------

(defn welcome
  "A ring handler to process all requests sent to the webapp."
  [request]
  {:status 200    
   :body "<h1>Hello, Clojure World</h1>
      <p>Welcome to your first Clojure app, I now update automatically</p>"  
   :headers {}})

(defn about
  "Information about the website developer"
  [request]
  {:status 200    
   :body "I am an awesome Clojure developer, well getting there..."  
   :headers {}})

(defn goodbye
  "A song to wish you goodbye"
  [request]
  {:status 200    
   :body "<h1>Walking back to happiness</h1>
          <p>Walking back to happiness with you</p>
          <p>Said, Farewell to loneliness I knew</p>
          <p>Laid aside foolish pride</p>"  
   :headers {}})

(defn hello
  "A simple personalised greeting showing the use of variable path element"
  [request]

  (let [name (get-in request [:route-params :name])]
    {:status 200    
     :body (str "Hello " name ". I got your name from the web URL")  
     :headers {}}))

(defn calculator
  "A list calculator"
  [request]

  (let [op (get-in request [:route-params :op])
        a (Integer. (get-in request [:route-params :a]))
        b (Integer. (get-in request [:route-params :b]))
        f (get operands op)]

    (if (false? (nil? f))    
      {:status 200    
       :body (str "(" op " " a " " b ")" " = " (f a b))  
       :headers {}}
      {:status 404
       :body "Sorry, unknown operator.  I only recognise + - * : (: is for division)"
       :headers {}})))


(defroutes app
  (GET "/" [] welcome)
  (GET "/bins" [] bins)
  (GET "/owners" [] owners)
  (GET "/goodbye" [] goodbye)
  (GET "/about" [] about)
  (GET "/request-info" [] handle-dump)
  (GET "/hello/:name" [] hello)
  (GET "/calculator/:op/:a/:b" [] calculator)
  (not-found "<h1>This is not the page you are looking for</h1>
   <p>Sorry, the page you requested wass not found!</p>"))

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

