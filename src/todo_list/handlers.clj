(ns todo-list.handlers
  (:require 
   [compojure.core :refer [defroutes GET]]
   [ring.handler.dump :refer [handle-dump]] 
   [compojure.route :refer [not-found]]
   )
  (:use
   [hiccup.core]
   [hiccup.page]))

(defn welcome
  "A ring handler to process all requests sent to the webapp."
  [request]
  (html [:h1 "Hello123, Clojure World"]
        [:p "Welcome to your first Clojure app, I now update automatically"]))

(defn goodbye
  "A song to wish you goodbye"
  [request]
  (html5 {:lang "en"}
         [:head (include-js "myscript.js") (include-css "mystyle.css")] 
         [:body 
          [:div [:h1 "Walking back to happiness"]]
          [:div [:p "Walking back to happiness with you"]]
          [:div [:p "Said, Farewell to loneliness I knew"]]
          [:div [:p "Laid aside foolish pride"]]
          [:div [:p "Learnt the truth from tears I cried"]]]))

(defn about
  "Information about the website developer"
  [request]
  {:status 200    
   :body "I am an awesome Clojure developer, well getting there..."  
   :headers {}})

(defn hello
  "A simple personalised greeting showing the use of variable path element"
  [request]

  (let [name (get-in request [:route-params :name])]
    {:status 200    
     :body (str "Hello " name ". I got your name from the web URL")  
     :headers {}}))

(def operands {"+" + "-" - "*" * ":" /})

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

(defroutes base
  (GET "/" [] welcome)
  (GET "/goodbye" [] goodbye)
  (GET "/about" [] about)
  (GET "/request-info" [] handle-dump)
  (GET "/hello/:name" [] hello)
  (GET "/calculator/:op/:a/:b" [] calculator)
  (not-found "<h1>This is not the page you are looking for</h1>
   <p>Sorry, the page you requested wass not found!</p>"))
