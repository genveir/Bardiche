(define (problem dungeon)
  (:domain dungeon)
  (:objects Mulak - personage
            startkamer kamer2 kamer3 - kamer
            staf - voorwerp)
  (:protagonist Mulak)
  (:init    (in Mulak startkamer)
            (in_vw staf startkamer)
            (intends Mulak (in_vw staf kamer2))
            (naast startkamer kamer2)
            (naast startkamer kamer3)
            (naast kamer2 kamer3)
            (geheim kamer2)
  )
            
  (:goal (and (in_vw staf kamer2)
              (not (in_vw staf startkamer)))
  )
)