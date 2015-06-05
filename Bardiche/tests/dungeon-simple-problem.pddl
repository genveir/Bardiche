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
            (in sprinkhaan1 startkamer)
            (heeft sprinkhaan1 klauwen)
            (wapen klauwen)
            (intends sprinkhaan1 (dood Hans))
  )
            
  (:bardichegoal 
    (good 
        (and (dood Hans)
             (not (dood Mulak))
             (dood sprinkhaan1))
        (and (not (dood Hans))
             (not (dood Mulak))
             (dood sprinkhaan1))
    )
    (bad (dood Mulak))
  )
)