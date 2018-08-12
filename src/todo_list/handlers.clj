(ns todo-list.handlers
  (:require 
   [compojure.core :refer [defroutes GET]]
   [ring.handler.dump :refer [handle-dump]] 
   [compojure.route :refer [not-found]]
   [clojure.string :refer [capitalize]]
   )
  (:use
   [hiccup.core]
   [hiccup.page]
   [hiccup.bootstrap.page]
   [hiccup.bootstrap.middleware]
   [ring.middleware.resource]))

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

(defn include-bootstrap-scripts
  []
  (list  [:script
          {:crossorigin "anonymous",
           :integrity
           "sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo",
           :src "https://code.jquery.com/jquery-3.3.1.slim.min.js"}]
         [:script
          {:crossorigin "anonymous",
           :integrity
           "sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49",
           :src
           "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"}]
         [:script
          {:crossorigin "anonymous",
           :integrity
           "sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy",
           :src
           "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"}]))

(defn include-bootstrap-css
  []
  (include-css "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"))

(defn alphabet 
  [request]

  
  (html5
   [:head
    [:title "Alphabet English"]
    (include-bootstrap-css)
    ]
   [:body
    [:h1  "Alphabet"]
    [:ul 
     [:li [:a {:href "alphabet/apple"} "Apple"]]
     [:li [:a {:href "alphabet/banana"} "Banana"]]
     [:li [:a {:href "alphabet/carrot"} "Carrot"]]
     [:li [:a {:href "alphabet/pear"} "Pear"]]]
    (include-bootstrap-scripts)]))

(defn page 
  [request]

  (let 
      [name (String. (get-in request [:route-params :name]))
       name-capitalized (capitalize name)]
    (html5
     [:head
      [:title name-capitalized]
      (include-bootstrap-css)
      ]
     [:body
      [:h1 name-capitalized ]
      [:div#carouselExampleSlidesOnly.carousel.slide
       {:data-ride "carousel"}
       [:div.carousel-inner 
        [:div.carousel-item.active
         [:img.d-block.w-100 {:alt "First slide", :src (str "/images/" name "1.jpg")}]]
        [:div.carousel-item
         [:img.d-block.w-100 {:alt "Second slide", :src (str "/images/" name "2.jpg")}]]
        [:div.carousel-item
         [:img.d-block.w-100 {:alt "Third slide", :src (str "/images/" name "3.jpg")}]]]] (include-bootstrap-scripts)])))

(defroutes base
  (GET "/" [] welcome)
  (GET "/goodbye" [] goodbye)
  (GET "/about" [] about)
  (GET "/request-info" [] handle-dump)
  (GET "/hello/:name" [] hello)
  (GET "/calculator/:op/:a/:b" [] calculator)
  (GET "/alphabet" [] alphabet)
  (GET "/alphabet/:name" [] page)
  (wrap-resource [] "public")
  (wrap-bootstrap-resources [])
  (not-found "<h1>This is not the page you are looking for</h1>
   <p>Sorry, the page you requested wass not found!</p>"))
