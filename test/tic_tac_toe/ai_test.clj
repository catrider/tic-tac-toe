(ns tic-tac-toe.ai-test
  (:require [tic-tac-toe.ai :as ai]
            [clojure.test :refer :all]))

(deftest next-move-test
  (testing "next move is one of the four corners"
    (let [possible-next-moves [[1 1] [1 3] [3 1] [3 3]]
          next-move (ai/next-move [[:e :e :e]
                                   [:e :e :e]
                                   [:e :e :e]] :x)]
      (is (reduce #(or %1 %2) (map #(= % next-move) possible-next-moves)))))
  (testing "next move is the center if the one of the corners is occupied and only one piece is on the board"
    (is (= [2 2] (ai/next-move [[:o :e :e]
                                [:e :e :e]
                                [:e :e :e]] :x))))
  (testing "next move completes a winner"
    (testing "horizontal winner"
      (let [board [[:x :e :x]
                   [:o :e :o]
                   [:o :e :e]]]
        (is (= [1 2] (ai/next-move board :x)))))
    (testing "vertical winner"
      (let [board [[:e :e :x]
                   [:o :o :e]
                   [:o :x :x]]]
        (is (= [2 3] (ai/next-move board :x)))))
    (testing "diagonal winner"
      (let [board [[:x :e :o]
                   [:o :x :e]
                   [:o :e :e]]]
        (is (= [3 3] (ai/next-move board :x))))
      (let [board [[:o :e :x]
                   [:e :x :o]
                   [:e :o :o]]]
        (is (= [3 1] (ai/next-move board :x)))))))

(deftest piece-locations-test
  (testing "it works"
    (let [board [[:x :o :x]
                 [:e :o :o]
                 [:x :x :e]]]
      (is (= #{[1 1] [1 3] [3 1] [3 2]} (ai/piece-locations board :x)))
      (is (= #{[1 2] [2 2] [2 3]} (ai/piece-locations board :o))))))

(deftest winning-move
  (testing "horizontal winning move"
    (let [board [[:o :e :o]
                 [:x :x :e]
                 [:e :o :e]]]
      (is (= [1 2] (ai/winning-move board :o))))
    (let [board [[:e :o :o]
                 [:e :x :x]
                 [:o :e :e]]]
      (is (= [2 1] (ai/winning-move board :x))))
    (let [board [[:x :e :e]
                 [:x :o :x]
                 [:o :o :e]]]
      (is (= [3 3] (ai/winning-move board :o)))))
  (testing "vertical winning move"
    (let [board [[:o :e :o]
                 [:o :x :e]
                 [:e :o :e]]]
      (is (= [3 1] (ai/winning-move board :o))))
    (let [board [[:o :o :x]
                 [:x :e :e]
                 [:e :o :e]]]
      (is (= [2 2] (ai/winning-move board :o))))
    (let [board [[:o :e :e]
                 [:x :o :x]
                 [:e :o :x]]]
      (is (= [1 3] (ai/winning-move board :x)))))
  (testing "diagonal winning move"
    (let [board [[:x :e :o]
                 [:o :x :e]
                 [:e :o :e]]]
      (is (= [3 3] (ai/winning-move board :x))))
    (let [board [[:o :o :x]
                 [:x :e :e]
                 [:x :o :e]]]
      (is (= [2 2] (ai/winning-move board :x))))))
