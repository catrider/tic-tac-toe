(ns tic-tac-toe.game-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.game :refer :all]))

(deftest game-over-test
  (testing "x winners"
    (testing "correctly detects horizontal winners"
      (let [board [[:e :o :x]
                   [:x :x :x]
                   [:o :o :e]]]
        (is (= :x (winner? board)))))
    (testing "correctly detects vertical winners"
      (let [board [[:e :o :x]
                   [:o :x :x]
                   [:o :o :x]]]
        (is (= :x (winner? board)))))
    (testing "correctly detects downward diagonal winners"
      (let [board [[:x :o :e]
                   [:x :x :o]
                   [:o :o :x]]]
        (is (= :x (winner? board)))))
    (testing "correctly detects upward diagonal winners"
      (let [board [[:e :o :x]
                   [:e :x :x]
                   [:x :o :e]]]
        (is (= :x (winner? board))))))
  (testing "o winners"
    (testing "correctly detects horizontal winners"
      (let [board [[:e :o :e]
                   [:x :x :o]
                   [:o :o :o]]]
        (is (= :o (winner? board)))))
    (testing "correctly detects vertical winners"
      (let [board [[:o :o :e]
                   [:o :x :o]
                   [:o :o :x]]]
        (is (= :o (winner? board)))))
    (testing "correctly detects downward diagonal winners"
      (let [board [[:o :o :e]
                   [:x :o :o]
                   [:o :x :o]]]
        (is (= :o (winner? board)))))
    (testing "correctly detects upward diagonal winners"
      (let [board [[:x :e :o]
                   [:x :o :o]
                   [:o :e :x]]]
        (is (= :o (winner? board))))))
  (testing "no winner"
    (testing "two in a row"
      (let [board [[:x :e :x]
                   [:x :o :o]
                   [:o :e :x]]]
        (is (= :undecided (winner? board)))))
    (testing "one in a row"
      (let [board [[:x :e :o]
                   [:e :e :e]
                   [:o :e :x]]]
        (is (= :undecided (winner? board)))))
    (testing "none in a row"
      (let [board [[:e :e :e]
                   [:e :e :e]
                   [:e :e :e]]]
        (is (= :undecided (winner? board)))))))

(deftest filled-test
  (testing "returns :x when all pieces are :x's"
    (is (= :x (filled? [:x :x :x]))))
  (testing "returns :o when all pieces are :o's"
    (is (= :o (filled? [:o :o :o]))))
  (testing "returns :undecided when all pieces are :e's"
    (is (= :undecided (filled? [:e :e :e]))))
  (testing "returns :undecided when two pieces are :x's"
    (is (= :undecided (filled? [:x :o :x]))))
  (testing "returns :undecided when two pieces are :o's"
    (is (= :undecided (filled? [:o :x :o])))))
