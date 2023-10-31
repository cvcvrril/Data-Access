select email_address, id_c from restaurant.customer as c, restaurant.orders as o
where c.id_c = o.id_cust order by id_c desc;

select email_address, id_c from restaurant.customer as c
join restaurant.orders as o on c.id_c = o.id_cust
order by id_c desc