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

(defn cell-html [rownum colnum cell with-submit?]
  [:td
   [:input {:name (str "b" rownum colnum)
            :value (str cell)
            :type (if with-submit?
                    "submit"
                    "button")}]])

(defn row-html [rownum row with-submit?]
  [:tr (map-indexed (fn [colnum cell]
                      (cell-html rownum colnum cell with-submit?))
                    row)])

(defn board-html [board with-submit?]
  (form-to [:post "/"]
           [:table
            (map-indexed (fn [rownum row]
                           (row-html rownum row with-submit?))
                         board)]))

(defn play-screen []
  (layout
    [:div {:class (str "center-div")}
     (hangman-image)
     [:p "Turn " (model/get-turn) ", choose a letter!"]
     [:p
	     (input-field)
	     (submit-letter)
     ]
       (board-html (model/get-board) true)]))

(defn start-screen []
  (layout
    [:div {:class (str "center-div")}
     [:p "Choose a word:"]
     [:p
	     (input-field-word)
	     (submit-letter)
     ]
       [:p "Turn " (model/get-total-guesses) ", choose a letter!"]
       (board-html (model/get-board) true)]))

(defn winner-screen [winner]
  (layout
    [:div
   [:p "The winner is: " winner]
   (board-html (model/get-board) false)
   (link-to "/" "Reset")]))

(defn draw-screen []
  (layout
    [:div
     [:p "It's a draw!"]
     (board-html (model/get-board) false)
     (link-to "/" "Reset")]))