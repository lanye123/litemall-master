// ɾ�����еĽڵ�͹�ϵ 
 MATCH(n) OPTIONAL MATCH (n)-[r]-() DELETE n,r
// ����Person �Ľڵ� 
CREATE (person:Person {cid:1,name:"����",age:24,gender:0,character:"A",money:1000,description:"���У���è��С˵�������꡷���˹�����Խ��ʿ�������ʮ����껭��ļ�֤�ߡ�����ò������˫����ʤ��Ů�ӣ����Ե������㣬���������ڣ��Ǹ�������Ӷ��顣�������ӽ���"});
CREATE (person:Person {cid:2,name:"�����",age:20,gender:1,character:"B",money:800,description:"��������������ͳ�������˽��Ů�����Ӿ硶�����꡷���Ů���˹�������������"});
CREATE (person:Person {cid:3,name:"���",age:49,gender:0,character:"A",money:8900,description:"��ۣ���������è��������ȨıС˵�������꡷�Ľ�ɫ֮һ��������Ļʵۣ�����װ������ͳһ"});

CREATE (person:Person {cid:4,name:"������",age:46,gender:1,character:"B",money:3700,description:"�������꡷��,����������˲�������ܸ���,�������ĸ��������ͦ���ӡ���������������ݾ�������Ĺ���,�ʵ۵�����,̫�ӵĹù�,������Ҫ��Ȩ���ƹ�,�������ĸ��"});
CREATE (person:Person {cid:5,name:"����������",age:47,gender:0,character:"A",money:1600,description:"���������ǵ��Ӿ硶�����꡷�ǳ�����������֮һ�����쵱�����࣬��������������ס�"});

CREATE (person:Person {cid:6,name:"Ҷ���",age:20,gender:1,character:"C",money:700,description:"Ҷ�������������è��������ȨıС˵�������꡷�Ľ�ɫ֮һ��������ĺ��ѣ����޸��˶�����"});
CREATE (person:Person {cid:7,name:"��Ʒ������С��",age:47,gender:0,character:"C",money:900,description:"һƷ���,��Ʒ��ߡ������ߵ���С�ұ��Ǿ�Ʒ,�����������һ�޶���������,��������������������"});
CREATE (person:Person {cid:8,name:"������",age:26,gender:0,character:"B",money:1700,description:"�������꡷��,�����ӽ����ɱ�����������Ӷ����Ҳ������ܴ�,��������ʵ�����Ʋ���ʲôˮ��,����ֻ�������ȴ�ʱ��"});
CREATE (person:Person {cid:9,name:"��������",age:25,gender:0,character:"A",money:1600,description:"�ڡ������꡷��,�˾����Ǳ˾���,����ͬ��֮��Ҳ�����˻�Ծ����������������ɫ�����ø�,�������������ɵĳ�����ȷ�ǰ����˷�������̫�������ľ���"});
CREATE (person:Person {cid:10,name:"������",age:46,gender:0,character:"C",money:1700,description:"�����꣬��������è��������ȨıС˵�������꡷�Ľ�ɫ֮һ��������Ժһ�������飬�ó�׷��֮����"});
CREATE (person:Person {cid:11,name:"����ʥŮ���Ķ��",age:21,gender:1,character:"A",money:2600,description:"���Ķ���Ǳ�����Ĳ�Ů,�������ΪʥŮ,�����Ǳ������ʦ��ɵĹ��ŵ���,�ڱ����Ҳ���Ǿ������ص�����"});
CREATE (person:Person {cid:12,name:"����С�ʵ�ս����",age:20,gender:0,character:"A",money:4600,description:"�ܶ�����֪�����еı���С�ʵ���˭��?��С��������ǰɡ� ս�����Ǳ���ڶ��λʵ�,��ǰ��κһ����ս���֮��,����ʦ��ɵ���ֶŮ��ͽ��"});

//������ϵ 
match(person:Person {name:"����"}),(person2:Person {name:"�����"}) create(person)-[r:Couple]->(person2);
match(person:Person {name:"����"}),(person2:Person {name:"������"}) create(person)-[r:Friends]->(person2);
match(person:Person {name:"����"}),(person2:Person {name:"����ʥŮ���Ķ��"}) create(person)-[r:Friends]->(person2);
match(person:Person {name:"����"}),(person2:Person {name:"���"}) create(person)-[r:Father]->(person2);
match(person:Person {name:"����"}),(person2:Person {name:"������"}) create(person)-[r:Wife_Mother]->(person2);
match(person:Person {name:"���"}),(person2:Person {name:"������"}) create(person)-[r:Son]->(person2);
match(person:Person {name:"���"}),(person2:Person {name:"������"}) create(person)-[r:BrotherSister]->(person2);
match(person:Person {name:"������"}),(person2:Person {name:"��������"}) create(person)-[r:Friends]->(person2);
match(person:Person {name:"����ʥŮ���Ķ��"}),(person2:Person {name:"����С�ʵ�ս����"}) create(person)-[r:Friends]->(person2);
match(person:Person {name:"�����"}),(person2:Person {name:"Ҷ���"}) create(person)-[r:Friends]->(person2);
match(person:Person {name:"�����"}),(person2:Person {name:"����������"}) create(person)-[r:Father]->(person2);
match(person:Person {name:"�����"}),(person2:Person {name:"������"}) create(person)-[r:Mother]->(person2);
match(person:Person {name:"������"}),(person2:Person {name:"��Ʒ������С��"}) create(person)-[r:Friends]->(person2);


�����ϵ��������  ����ɾ����ϵ 
match p = (:Person {name:"�����"})-[r:Couple]-(:Person)  delete r 

�����˹�ϵ�ķ���
match data=(na:Person {name:"����"})-[*2..2]->(nb:Person) return nb

�����ǹ�ϵ�ķ��� 
match data=(na:Person {name:"����"})-[*2..2]-(nb:Person) return nb


shortestPath��������·��������̵�path
MATCH p=shortestPath( (person:Person {name:"������"})-[*]-(person2:Person {name:"��Ʒ������С��"}) ) 
   RETURN length(p), nodes(p)

��ʹ�� shortestPath
MATCH p=(person:Person {name:"������"})-[*]-(person2:Person {name:"��Ʒ������С��"}) 
   RETURN p 
MATCH p=shortestPath( (person:Person {name:"������"})-[*]-(person2:Person {name:"��Ʒ������С��"}) ) 
   where length(p)>1  RETURN p

explain MATCH p=shortestPath( (person:Person {name:"������"})-[*]-(person2:Person {name:"��Ʒ������С��"}) ) 
   where length(p)>1  RETURN p

explain MATCH p=shortestPath( (person:Person {name:"������"})-[*]-(person2:Person {name:"��Ʒ������С��"}) ) 
   with p where length(p)>1  RETURN p

ȫ������ 
CALL db.index.fulltext.createNodeIndex("nameAndDescription",["Person"],["name", "description"])
CALL db.index.fulltext.queryNodes("nameAndDescription", "����") YIELD node, score
RETURN node.title, node.description, score

