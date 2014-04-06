# Raev

Raev - a colourlovers (http://www.colourlovers.com) API wrapper/interface for Clojure

*Currently only the palettes section is supported.*

## Usage

* Add `[se.vorce/raev "0.1.0-SNAPSHOT"]` to your :dependencies in the project.clj file
* Add raev.core to your namespace `(:use raev.core)`, in the source file where you want to use raev from

Now you can use raev.

### Examples

   (raev.core/get-palette-random) ;; Returns one random Palette
   (raev.core/get-palettes-with {raev.core/PARAM_HEX "FF0000"}) ;; Get 20 Palettes with red in them
   (raev.core/colourlovers-json (str "api/palettes/" raev.core/MODIFIER_NEW) {raev.core/PARAM_KEYWORDS "space"}) ;; Get a raw reply of new palettes matching the keyword space

#### Full Quil example

It's a bit messy since I just wanted to hack something together, but here's a real example that uses Raev
https://gist.github.com/vorce/10004442

## License

Copyright © 201 - 2014 Joel Carlbark

Distributed under the Eclipse Public License, the same as Clojure.
