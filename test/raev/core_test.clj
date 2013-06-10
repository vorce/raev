(ns raev.core-test
  (:use clojure.test
        raev.core)
  (:import raev.core.Palette))

(deftest colors-from-random-palette
  (testing "Should get at least one entry in colors from random palette"
           (let [ palette (get-palette-random) ]
             (is (>= (count (:colors palette)) 1))
           )
  )
)

(defn palette-with-red [p]
  (let [ colors (:colors p) ]
    (in? "FF0000" colors)
    )
  )

(deftest palettes-containing-red
  (testing "Asking for palettes with red in them should give us palettes containing red"
           (let [ palettes (get-palettes-with {PARAM_HEX "FF0000"})
                  reds (filter palette-with-red palettes) ]
             (is (= (count palettes) (count reds)))
             )
  ))
  