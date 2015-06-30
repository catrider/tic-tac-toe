(ns tic-tac-toe.board-test
  (:require [clojure.test :refer :all]
            [tic-tac-toe.board :as board]))

(deftest insert-test
  (testing "returns a board with an inserted :o"
    (let [board [[:e :e :e]
                 [:e :e :e]
                 [:e :e :e]]
          expected-board [[:e :e :e]
                          [:e :e :o]
                          [:e :e :e]]]
      (is (= (board/insert board 2 3 :o) expected-board))))
  (testing "throws an AssertionError if the coordinate is occupied"
    (let [board [[:e :e :e]
                 [:e :e :x]
                 [:e :e :e]]]
      (is (thrown? AssertionError (board/insert board 2 3 :o)))))
  (testing "throws an AssertionError if the x coordinate is less than 1"
    (is (thrown? AssertionError (board/insert [[] [] []] 0 2 :x))))
  (testing "throws an AssertionError if the x coordinate is greater than 3"
    (is (thrown? AssertionError (board/insert [[] [] []] 4 2 :x))))
  (testing "throws an AssertionError if the y coordinate is less than 1"
    (is (thrown? AssertionError (board/insert [[] [] []] 2 0 :x))))
  (testing "throws an AssertionError if the y coordinate is greater than 3"
    (is (thrown? AssertionError (board/insert [[] [] []] 2 4 :x)))))

(deftest occupied-test
  (testing "returns true if the coordinate is occupied"
    (let [board [[:e :x :o]
                 [:x :e :e]
                 [:o :e :x]]]
      (is (= true (board/occupied? board 1 2)))
      (is (= true (board/occupied? board 1 3)))
      (is (= true (board/occupied? board 2 1)))
      (is (= true (board/occupied? board 3 1)))
      (is (= true (board/occupied? board 3 3)))))
  (testing "returns false if the coordinate is not occupied"
    (let [board [[:e :x :o]
                 [:x :e :e]
                 [:o :e :x]]]
      (is (= false (board/occupied? board 1 1)))
      (is (= false (board/occupied? board 2 2)))
      (is (= false (board/occupied? board 2 3)))
      (is (= false (board/occupied? board 3 2)))))
  (testing "throws an AssertionError if the x coordinate is less than 1"
    (is (thrown? AssertionError (board/occupied? [[] [] []] 0 2))))
  (testing "throws an AssertionError if the x coordinate is greater than 3"
    (is (thrown? AssertionError (board/occupied? [[] [] []] 4 2))))
  (testing "throws an AssertionError if the y coordinate is less than 1"
    (is (thrown? AssertionError (board/occupied? [[] [] []] 2 0))))
  (testing "throws an AssertionError if the y coordinate is greater than 3"
    (is (thrown? AssertionError (board/occupied? [[] [] []] 2 4)))))

(deftest filled-test
  (testing "it works"
    (let [board [[:x :o :x]
                 [:o :x :x]
                 [:o :x :o]]]
      (is (= true (board/filled? board))))
    (let [board [[:x :o :x]
                 [:o :x :e]
                 [:o :x :o]]]
      (is (= false (board/filled? board))))))
