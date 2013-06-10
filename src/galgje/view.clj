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
    :required (str "required")
		:pattern (str "[A-Za-z]")
		:type (str "text")
    :autofocus (str "autofocus")
	}]
)


(defn input-field-word []
	[:input {
    :class (str "inputWord")
		:name (str "word")
		:maxlength 20
    :required (str "required")
		:pattern (str "[A-Za-z]*")
		:type (str "text")
    :autofocus (str "autofocus")
	}]
)

(defn submit-letter [disabled]
	[:input {
    :disabled (boolean disabled)
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
	[:div {:class (str "remainingChars")}
		(model/get-remaining-characters)
	]
)

(defn play-screen []
	(layout
		[:div {:class (str "center-div")}
			[:div {:class (str "inputDiv")}
				(form-to [:post "/"]
					(input-field)
					(submit-letter (boolean false)))
				(guessed-chars)
			]
			[:div {:class (str "hangman") }
				[:p "Turn " (model/get-total-guesses) ", choose a letter!"]

					(remaining-characters)
					(hangman-image)
			]
			[:div {:class (str "clearDiv")}]
		]
	)
)

(defn start-screen []
	(layout
		[:div {:class (str "center-div")}
     [:div {:class (str "startDiv")}
				[:h2 "Choose a word:"]
				[:p
					(form-to [:post "/startgame"]
						(input-field-word)
						(submit-letter (boolean false))
            [:p {:class (str "smallP")} "Maximum of 20 characters"])
				]
	   ]
		]
	)
)
(defn loser-screen []
	(layout
		[:div {:class (str "center-div")}
			[:div {:class (str "inputDiv")}
				(form-to [:post "/"]
					(input-field )
					(submit-letter (boolean true)))
				(guessed-chars)
			]
   [:div {:class (str "notificationDiv")}
			[:h2 "You've lost!"]
				(link-to {:class (str "reset")} "/" "Reset")
		]
			[:div {:class (str "hangman") }
				[:p "Turn " (model/get-total-guesses) ", choose a letter!"]

					(remaining-characters)
					(hangman-image)
			]
			[:div {:class (str "clearDiv")}]
		]
	)
)

(defn winner-screen []
  (layout
		[:div {:class (str "center-div")}
			[:div {:class (str "inputDiv")}
				(form-to [:post "/"]
					(input-field)
					(submit-letter (boolean true)))
				(guessed-chars)
			]
   [:div {:class (str "notificationDiv")}
			[:h2 "You've guessed the word!"]
				(link-to {:class (str "reset")} "/" "Reset")
		]
			[:div {:class (str "hangman") }
				[:p "Turn " (model/get-total-guesses) ", choose a letter!"]

					(remaining-characters)
					(hangman-image)
			]
			[:div {:class (str "clearDiv")}]
		]
	)
)