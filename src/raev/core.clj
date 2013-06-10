(ns raev.core
  (:require [clj-http.client :as client]))

(defrecord Palette [rank image-url num-hearts num-votes num-views date-created
                    title url user-name badge-url api-url id
                    description colors num-comments])

(def COLOUR_LOVERS_BASE_URL "http://www.colourlovers.com/")
(def API_PATH "api")
(def JSON_FORMAT "json")

(def SECTION_PALETTES "palettes")
(def SECTION_COLORS "colors")
(def SECTION_PATTERNS "patterns")
(def SECTION_LOVERS "lovers")
(def SECTION_STATS "stats")
(def SECTION_LIST [SECTION_COLORS SECTION_PALETTES SECTION_PATTERNS
                   SECTION_LOVERS SECTION_STATS])

(def MODIFIER_RANDOM "random")
(def MODIFIER_NEW "new")
(def MODIFIER_TOP "top")

(def PARAM_HEX "hex") ;; Ex: 00FF33
(def PARAM_KEYWORDS "keywords") ;; Ex: search+term
(def PARAM_NUMRESULTS "numResults") ;; 20 is default, 100 max
(def PARAM_FORMAT "format") ;; Valid ones: xml, json

(defn in? 
  "true if coll contains elm"
  [elm coll]  
  (some #(= elm %) coll))


(defn colourlovers-json
  "Execute a query against colourlovers' API, requesting the reply to be in json. Ex: (colourlovers-json (str API_PATH SECTION_PALETTES MODIFIER_NEW) {PARAM_KEYWORDS \"space\"})"
  ([query] (colourlovers-json query {}))
  ([query params]
    (client/get
      (str COLOUR_LOVERS_BASE_URL query)
      {:query-params (merge params {PARAM_FORMAT JSON_FORMAT}) :as :json})
    ))

(defn- create-query
  "Create a query against a specific section and with optional param(s).
   Valid sections and arguments, see: http://www.colourlovers.com/api"
   ([section] (create-query section ""))
   ([section modifier]
     (let [ m (if (empty? modifier) ""
                (str "/" modifier)) ]
       (str API_PATH "/" section m)
       )
     ))

(defn- create-query-palettes-random
  "Create a query for a random palette"
  []
  (create-query SECTION_PALETTES MODIFIER_RANDOM))

(defn- create-palette-from-reply
  "JSON reply to a Palette structure"
  [reply]
  (Palette. (:rank reply) (:imageUrl reply) (:numHearts reply) (:numVotes reply)
            (:numViews reply) (:dateCreated reply) (:title reply) (:url reply)
            (:userName reply) (:badgeUrl reply) (:apiUrl reply) (:id reply)
            (:description reply) (:colors reply) (:numComments reply)))

(defn get-palette-random
  "Get one random palette from colourlovers"
  []
  (let [ reply (colourlovers-json (create-query-palettes-random))
         p (first (:body reply)) ]
    (create-palette-from-reply p)))

(defn get-palettes-with
  "Get palettes matching the parameters from colourlovers"
  [params]
  (let [ raw (colourlovers-json (create-query SECTION_PALETTES) params) 
         reply (:body raw) ]
    (for [r reply] (create-palette-from-reply r))))
