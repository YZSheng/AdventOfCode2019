(ns advent-of-code.day-two
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(str/split "1,0,0,0,99" #",")

(defn format-command
  "formats a string into a vector"
  [str-command]
  (->> (str/split str-command #",")
       (map read-string)
       vec))

(format-command "1,0,0,0,99")

(defn handle-op
  [command sub-command op]
  (let [input-one (nth command (nth sub-command 1))
        input-two (nth command (nth sub-command 2))
        result-pos (nth sub-command 3)
        result (op input-one input-two)]
    (assoc command result-pos result)))

(defn handle-add
  [command sub-command]
  (handle-op command sub-command +))

(handle-add (format-command "1,0,0,0,99") (format-command "1,0,0,0"))

(defn handle-multiply
  [command sub-command]
  (handle-op command sub-command *))

(handle-multiply (format-command "2,3,0,3,99") (format-command "2,3,0,3"))
(handle-multiply (format-command "2,4,4,5,99,0") (format-command "2,4,4,5,99"))

(defn take-sub-command
  [command n]
  (vec (->> (iterate inc n)
            (take 4)
            (map #(nth command % 0)))))

(take-sub-command [1 0 0 0 99] 4)

(defn process-command
  [command]
  (loop [command command
         index 0]
    (let [sub-command (take-sub-command command index)]
      (case (first sub-command)
        99 command
        1 (recur (handle-add command sub-command) (+ 4 index))
        2 (recur (handle-multiply command sub-command) (+ 4 index))))))

(process-command [1 0 0 0 99])

(->> "1,0,0,0,99"
     format-command
     process-command)

(->> "2,3,0,3,99"
     format-command
     process-command)

(->> "2,4,4,5,99,0"
     format-command
     process-command)

(->> "1,9,10,3,2,3,11,0,99,30,40,50"
     format-command
     process-command)

(defn read-file [path]
  (with-open [rdr (io/reader path)]
    (doall (line-seq rdr))))

(-> "resources/day_two_part_one.txt"
    read-file
    first
    format-command
    (assoc 1 12)
    (assoc 2 2)
    process-command
    first)