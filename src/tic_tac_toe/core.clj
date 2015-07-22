(ns tic-tac-toe.core
  (:require [tic-tac-toe.game :as game]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.piece :as piece]
            [tic-tac-toe.printer :as printer]
            [tic-tac-toe.ai :as ai]))

(defn parse-loc [loc]
  (map (comp #(Integer/parseInt %) clojure.string/trim) (clojure.string/split loc #",")))

(defn read-location-input []
  (try 
    (println "Play your piece <\"x,y\">: ")
    (let [[x y :as loc] (doall (parse-loc (first (line-seq (java.io.BufferedReader. *in*)))))]
      (if (or (< x 1) (> x 3) (< y 1) (> y 3))
        (throw (Exception. ""))
        loc)) 
    (catch Throwable e
      (do
        (println "Location must be of form 'x,y', x and y being numbers 1-3")
        (read-location-input)))))

(defn random-piece []
  (->> (list :x :o)
       (shuffle)
       (first)))

(defn start []
  (let [initial-board [[:e :e :e]
                      [:e :e :e]
                      [:e :e :e]]]
    (println "Tic-Tac-Toe!")
    (loop [board initial-board
           piece-up (random-piece)
           winner :undecided]
      (do
        (println (printer/print-board board))
        (println (printer/piece-to-char piece-up) "is up") 
        (let [[x,y] (if (= :x piece-up)
                      (read-location-input)
                      (ai/next-move board piece-up))
              
              new-board (if (board/occupied? board x y)
                          (do
                            (println (str "Location (" x "," y ") is already occupied."))
                            board)
                          (board/insert board x y piece-up))
              new-piece-up (if (= new-board board)
                             piece-up
                             (piece/other piece-up))
              new-winner (game/winner? new-board)]
          (cond 
           (or (= new-winner :x) (= new-winner :o)) (do
                                                      (println (printer/print-board new-board))
                                                      (println (printer/piece-to-char piece-up) "wins!"))
           (board/filled? new-board) (println "\nDraw!")
           :else (recur new-board new-piece-up new-winner)))))
    (println "Play again? (y/n): ")
    (let [answer (first (line-seq (java.io.BufferedReader. *in*)))]
      (if (= "y" answer)
        (recur)))))
