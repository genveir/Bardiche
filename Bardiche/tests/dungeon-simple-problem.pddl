(define (problem dungeon)
  (:domain dungeon)
  (:objects Mulak Hans sprinkhaan1 - personage
            startkamer - kamer
            staf klauwen - voorwerp)
  (:protagonist Mulak)
  (:init    (in Mulak startkamer)
            (heeft Mulak staf)
            (wapen staf)
            (in Hans startkamer)
            (vriend Mulak Hans)
            (in sprinkhaan1 startkamer)
            (heeft sprinkhaan1 klauwen)
            (wapen klauwen)
            (intends sprinkhaan1 (dood Hans))
  )
            
  (:goal (or 
            (dood sprinkhaan1)
            (and (dood Mulak)
                 (dood Hans))
         )
  )
)