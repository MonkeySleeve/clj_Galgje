(ns galgje.view
  (:use hiccup.form
        [hiccup.def :only [defhtml]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [html5 include-css]])
  (:require [galgje.model :as model]))

(defhtml layout [& content]
  (html5
   [:head
    [:title "Welcome to galgje-luminus"]
    (include-css "/css/galgje.css")]
   [:body [:div#wrapper content]]))

(defn play-screen []
  (layout
    [:div {:class (str "center-div")}
     (input-field)
     (submit-letter)
     (hangman-image)
       [:p "Turn " (model/get-total-guesses) ", choose a letter!"]]))

(defn input-field []
  [:input {:name (str "guess")
           :maxlength 1
           :pattern (str "[A-Za-z]")
           :type (str "text")}])

(defn input-field-word []
  [:input {:name (str "word")
           :maxlength 16
           :pattern (str "[A-Za-z]")
           :type (str "text")}])

(defn submit-letter []
  [:input {:name (str "submit")
           :type (str "submit")
           :value (str "Go!") }])

(defn hangman-image []
  [:div {:id (str "img-holder")}
    [:img {
      :src (str "/images/hangman" (model/get-total-guesses) ".png")
      :alt (str "Hangman")
    }]
  ]
)

(defn start-screen []
  (layout
    [:div {:class (str "center-div")}
     [:p "Choose a word:"]
     [:p
      (form-to [:post "/"]
	     (input-field-word)
	     (submit-letter))
     ]
       [:p "Turn " (model/get-total-guesses) ", choose a letter!"]
       (board-html (model/get-board) true)]))

(defn winner-screen [winner]
  (layout
    [:div
   [:p "The winner is: " winner]
   (link-to "/" "Reset")]))
