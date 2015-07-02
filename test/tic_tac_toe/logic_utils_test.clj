(ns tic-tac-toe.logic-utils-test
  (:require [tic-tac-toe.logic-utils :as logic-utils]
            [clojure.test :refer :all]))

(deftest or=-test
  (testing "it works"
    (is (= true (logic-utils/or= 4 1 2 3 4 5)))
    (is (= false (logic-utils/or= 4 5 3 2 6 7 1)))))
