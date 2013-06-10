(ns galgje.view
	(:use hiccup.form
		[hiccup.def :only [defhtml]]
		[hiccup.element :only [link-to]]
		[hiccup.page :only [html5 include-css]])
	(:require [galgje.model :as model])
)

(defhtml layout [& content]
	(html5
		[:head
			[:title "Welcome to galgje-luminus"]
			(include-css "/css/galgje.css")]
		[:body
			[:div#wrapper content]
		]
	)
)

(defn guessed-chars []
	[:div {:id (str "guessChars")}
		[:p (str "Guessed characters: ") ]
		[:p {:class (str "chars")}
			(model/get-guessed-characters)
		]
	]
)

(defn input-field []
	[:input {
		:name (str "guess")
		:maxlength 1
		:pattern (str "[A-Za-z]")
		:type (str "text")
    :autofocus (str "autofocus")
	}]
)

(defn submit-letter []
	[:input {
		:name (str "submit")
		:type (str "submit")
		:value (str "Go!")
	}]
)

(defn hangman-image []
	[:div {:id (str "img-holder")}
		[:img {
			:src (str "/images/hangman" (model/get-total-guesses) ".png")
			:alt (str "Hangman")
		}]
	]
)

(defn remaining-characters []
	[:div {:style (str "font-size: 20px; letter-spacing: 1.3em;")}
		(model/get-remaining-characters)
	]
)

(defn play-screen []
	(layout
		[:div {:class (str "center-div")}
			[:div {:class (str "inputDiv")}
				(form-to [:post "/"]
					(input-field)
					(submit-letter))
				(guessed-chars)
			]
			[:div {:class (str "hangman") }
				[:p "Turn " (model/get-total-guesses) ", word is: " (model/get-word)", choose a letter!"]

					(hangman-image)
					(remaining-characters)
			]
			[:div {:class (str "clearDiv")}]
		]
	)
)

(defn input-field-word []
	[:input {
		:name (str "word")
		:maxlength 16
		:pattern (str "[A-Za-z]")
		:type (str "text")
	}]
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
		]
	)
)

(defn winner-screen []
  (layout
		[:div {:class (str "center-div")}
			[:div {:class (str "inputDiv")}
				(form-to [:post "/"]
					(input-field)
					(submit-letter))
				(guessed-chars)
			]
   [:div {:class (str "notificationDiv")}
			[:h2 "You've guessed the word!"]
				(link-to "/" "Reset")
		]
			[:div {:class (str "hangman") }
				[:p "Turn " (model/get-total-guesses) ", word is: " (model/get-word)", choose a letter!"]

					(hangman-image)
					(remaining-characters)
			]
			[:div {:class (str "clearDiv")}]
		]
	)
)

(defn loser-screen []
	(layout
		[:div {:class (str "center-div")}
			[:div {:class (str "inputDiv")}
				(form-to [:post "/"]
					(input-field)
					(submit-letter))
				(guessed-chars)
			]
   [:div {:class (str "notificationDiv")}
			[:h2 "You've lost!"]
				(link-to "/" "Reset")
		]
			[:div {:class (str "hangman") }
				[:p "Turn " (model/get-total-guesses) ", word is: " (model/get-word)", choose a letter!"]

					(hangman-image)
					(remaining-characters)
			]
			[:div {:class (str "clearDiv")}]
		]
	)
)