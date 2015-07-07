(ns tic-tac-toe.ai-test
  (:require [tic-tac-toe.ai :as ai]
            [tic-tac-toe.logic-utils :as lu]
            [clojure.test :refer :all]))

(deftest next-move-test
  (testing "next move is one of the four corners"
    (let [next-move (ai/next-move [[:e :e :e]
                                   [:e :e :e]
                                   [:e :e :e]] :x)]
      (is (lu/or= next-move [1 1] [1 3] [3 1] [3 3]))))
  (testing "next move is the center if the one of the corners is occupied and only one piece is on the board"
    (is (= [2 2] (ai/next-move [[:o :e :e]
                                [:e :e :e]
                                [:e :e :e]] :x))))
  (testing "next move is not the center if it is occupied"
    (is (not= [2 2] (ai/next-move [[:e :e :e]
                                   [:e :o :e]
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
        (is (= [3 1] (ai/next-move board :x))))))
  (testing "next moves prevents a winner for the opponent"
    (testing "horizontal winner"
      (let [board [[:x :e :x]
                   [:o :e :o]
                   [:o :e :e]]]
        (is (= [1 2] (ai/next-move board :x)))))
    (testing "vertical winner"
      (let [board [[:e :e :x]
                   [:x :o :e]
                   [:o :x :x]]]
        (is (= [2 3] (ai/next-move board :o)))))
    (testing "diagonal winner"
      (let [board [[:x :e :o]
                   [:o :x :e]
                   [:o :e :e]]]
        (is (= [3 3] (ai/next-move board :o))))
      (let [board [[:o :e :x]
                   [:e :x :o]
                   [:e :o :e]]]
        (is (= [3 1] (ai/next-move board :o))))))
  (testing "next move intelligently plays a corner for non game ending plays"
    (let [board [[:o :e :e]
                 [:e :x :e]
                 [:e :e :e]]
          next-move (ai/next-move board :o)]
      (do (println next-move) (is (lu/or= next-move [3 1] [1 3]))))
    (let [board [[:o :e :e]
                 [:x :e :e]
                 [:e :e :e]]]
      (is (= (ai/next-move board :o) [1 3])))
    (let [board [[:x :e :e]
                 [:o :e :e]
                 [:x :o :e]]]
      (is (lu/or= (ai/next-move board :x) [2 2] [1 3])))
    (let [board [[:e :e :e]
                 [:x :o :e]
                 [:e :x :e]]]
      (is (= (ai/next-move board :o) [1 3]))))
  (testing "always favors a move that could lead to tic-tac-toe"
    (let [board [[:e :e :x]
                 [:x :o :o]
                 [:e :e :x]]]
      (is (lu/or= (ai/next-move board :o) [1 2] [3 2])))
    (let [board [[:o :x :o]
                 [:e :x :e]
                 [:e :o :x]]]
      (is (lu/or= (ai/next-move board :o) [2 1] [3 1])))))

(deftest piece-locations-test
  (testing "it works"
    (let [board [[:x :o :x]
                 [:e :o :o]
                 [:x :x :e]]]
      (is (= #{[1 1] [1 3] [3 1] [3 2]} (ai/piece-locations board :x)))
      (is (= #{[1 2] [2 2] [2 3]} (ai/piece-locations board :o))))))

(deftest adjacent-test
  (testing "it works"
    (is (= true (ai/adjacent? [1 3] [3 3])))
    (is (= true (ai/adjacent? [1 3] [1 1])))
    (is (= false (ai/adjacent? [1 3] [3 1])))
    (is (= false (ai/adjacent? [1 3] [3 2])))
    (is (= false (ai/adjacent? [1 3] [2 3])))
    (is (= true (ai/adjacent? [2 2] [3 3])))
    (is (= true (ai/adjacent? [1 1] [2 2])))))

(deftest adjacent-to-played-piece?-test
  (testing "it works"
    (let [board [[:o :e :e]
                 [:e :x :e]
                 [:e :e :e]]]
      (is (= true (ai/adjacent-to-played-piece? board :o [3 1])))
      (is (= true (ai/adjacent-to-played-piece? board :o [1 3])))
      (is (= false (ai/adjacent-to-played-piece? board :o [3 3])))
      (is (= true (ai/adjacent-to-played-piece? board :x [1 3])))
      (is (= true (ai/adjacent-to-played-piece? board :x [3 1])))
      (is (= true (ai/adjacent-to-played-piece? board :x [3 3]))))))

(deftest tangentially-aligned-with-opponent-piece?-test
  (testing "it works"
    (let [board [[:o :e :e]
                 [:e :x :e]
                 [:e :e :e]]]
      (is (= false (ai/tangentially-aligned-with-opponent-piece? board :x [3 3])))
      (is (= false (ai/tangentially-aligned-with-opponent-piece? board :o [3 3])))
      (is (= true (ai/tangentially-aligned-with-opponent-piece? board :o [3 2])))
      (is (= false (ai/tangentially-aligned-with-opponent-piece? board :x [3 2])))
      (is (= true (ai/tangentially-aligned-with-opponent-piece? board :x [2 1])))
      (is (= true (ai/tangentially-aligned-with-opponent-piece? board :o [2 1]))))))

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
