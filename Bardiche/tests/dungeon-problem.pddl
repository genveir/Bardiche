(define (problem dungeon)
  (:domain dungeon)
  (:requirements :adl)
  (:objects Mulak Hans Balou Cherry Henk sprinkhaan1 sprinkhaan2 sprinkhaan3 - personage
            startkamer gang_west gang_noord gang_oost gang_zuid deuren zwembadkamer wapenkamer - kamer
            staf platemail mace chainshirt greataxe leather alchemist_fire zwaard javelin klauwen - voorwerp)
  (:protagonist Mulak)
  (:init    (in Mulak startkamer)
            (in Hans startkamer)
            (in Balou startkamer)
            (in Cherry startkamer)
            (in Henk startkamer)
            (heeft Mulak staf)
            (wapen staf)
            (heeft Hans platemail)
            (heeft Hans mace)
            (wapen mace)
            (heeft Balou chainshirt)
            (heeft Balou greataxe)
            (wapen greataxe)
            (heeft Cherry leather)
            (heeft Cherry alchemist_fire)
            (wapen alchemist_fire)
            (heeft Henk platemail)
            (heeft Henk zwaard)
            (wapen zwaard)
            (heeft Henk javelin)
            (wapen javelin)
            
            (intends Mulak (not (dood Mulak)))
            (vriend Mulak Hans)
            
            (in sprinkhaan1 startkamer)
            (heeft sprinkhaan1 klauwen)
            (in sprinkhaan2 zwembadkamer)
            (heeft sprinkhaan2 klauwen)
            (in sprinkhaan3 zwembadkamer)
            (heeft sprinkhaan3 klauwen)
            (wapen klauwen)
            
            (intends sprinkhaan1 (dood Mulak))
            (intends sprinkhaan1 (dood Hans))
            (intends sprinkhaan1 (dood Cherry))
            (intends sprinkhaan1 (dood Balou))
            (intends sprinkhaan1 (dood Henk))
            (intends sprinkhaan1 (in sprinkhaan1 wapenkamer))
            
            (intends sprinkhaan2 (dood Mulak))
            (intends sprinkhaan2 (dood Hans))
            (intends sprinkhaan2 (dood Cherry))
            (intends sprinkhaan2 (dood Balou))
            (intends sprinkhaan2 (dood Henk))
            (intends sprinkhaan2 (samen_en_levend sprinkhaan2 sprinkhaan1))
            
            (intends sprinkhaan3 (dood Mulak))
            (intends sprinkhaan3 (dood Hans))
            (intends sprinkhaan3 (dood Cherry))
            (intends sprinkhaan3 (dood Balou))
            (intends sprinkhaan3 (dood Henk))
            (intends sprinkhaan3 (samen_en_levend sprinkhaan3 sprinkhaan1))
            
            (naast startkamer gang_west)
            (naast startkamer gang_oost)
            (naast startkamer gang_noord)
            (naast startkamer gang_zuid)
            (naast gang_zuid deuren)
            (naast gang_west zwembadkamer)
            (naast gang_west wapenkamer)
            
            (geheim wapenkamer)
            (in_vw zwaard wapenkamer)
            (in_vw chainshirt wapenkamer)
  )
  
  (:goal (dood sprinkhaan1)
  )
)