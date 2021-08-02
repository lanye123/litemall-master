// 删除所有的节点和关系 
 MATCH(n) OPTIONAL MATCH (n)-[r]-() DELETE n,r
// 创建Person 的节点 
CREATE (person:Person {cid:1,name:"范闲",age:24,gender:0,character:"A",money:1000,description:"范闲，是猫腻小说《庆余年》主人公，穿越人士，庆国数十年风雨画卷的见证者。其容貌俊美无双，尤胜于女子，生性淡薄刚毅，善良而腹黑，城府极深，重视恩情。最终隐居江南"});
CREATE (person:Person {cid:2,name:"林婉儿",age:20,gender:1,character:"B",money:800,description:"林婉儿是庆国宰相和长公主的私生女，电视剧《庆余年》里的女主人公，由李沁饰演"});
CREATE (person:Person {cid:3,name:"庆帝",age:49,gender:0,character:"A",money:8900,description:"庆帝，网文作家猫腻所著的权谋小说《庆余年》的角色之一，南庆国的皇帝，心中装有天下统一"});

CREATE (person:Person {cid:4,name:"长公主",age:46,gender:1,character:"B",money:3700,description:"《庆余年》中,长公主这个人不仅人设很复杂,就连她的感情生活还是挺复杂。长公主所处的身份就是庆国的公主,皇帝的妹妹,太子的姑姑,国家重要财权的掌管,林婉儿的母亲"});
CREATE (person:Person {cid:5,name:"宰相林若甫",age:47,gender:0,character:"A",money:1600,description:"林若甫，是电视剧《庆余年》登场的虚拟人物之一，南庆当朝宰相，林婉儿的亲生父亲。"});

CREATE (person:Person {cid:6,name:"叶灵儿",age:20,gender:1,character:"C",money:700,description:"叶灵儿，网文作家猫腻所著的权谋小说《庆余年》的角色之一，林婉儿的好友，最后嫁给了二皇子"});
CREATE (person:Person {cid:7,name:"九品射手燕小乙",age:47,gender:0,character:"C",money:900,description:"一品最低,九品最高。庆帝身边的燕小乙便是九品,而且是庆国独一无二的神射手,臂力、眼力、听力惊人"});
CREATE (person:Person {cid:8,name:"二皇子",age:26,gender:0,character:"B",money:1700,description:"《庆余年》中,二皇子结局自杀身亡。二皇子对庆帝也是意见很大,但以他的实力还掀不起什么水花,所以只能慢慢等待时机"});
CREATE (person:Person {cid:9,name:"靖王世子",age:25,gender:0,character:"A",money:1600,description:"在《庆余年》中,此靖王非彼靖王,但是同音之美也会让人会对靖王世子李弘成这个角色产生好感,而靖王世子李弘成的出场的确是帮助了范闲逃脱太子势力的纠缠"});
CREATE (person:Person {cid:10,name:"王启年",age:46,gender:0,character:"C",money:1700,description:"王启年，网文作家猫腻所著的权谋小说《庆余年》的角色之一，庆国监察院一处的文书，擅长追踪之术。"});
CREATE (person:Person {cid:11,name:"北齐圣女海棠朵朵",age:21,gender:1,character:"A",money:2600,description:"海棠朵朵是北齐国的才女,被人尊称为圣女,而且是北齐大宗师苦荷的关门弟子,在北齐国也算是举足轻重的人物"});
CREATE (person:Person {cid:12,name:"北齐小皇帝战豆豆",age:20,gender:0,character:"A",money:4600,description:"很多人想知道剧中的北齐小皇帝是谁呢?让小编告诉你们吧。 战豆豆是北齐第二任皇帝,乃前北魏一代大将战清风之孙,大宗师苦荷的叔侄女兼徒孙"});

//创建关系 
match(person:Person {name:"范闲"}),(person2:Person {name:"林婉儿"}) create(person)-[r:Couple]->(person2);
match(person:Person {name:"范闲"}),(person2:Person {name:"王启年"}) create(person)-[r:Friends]->(person2);
match(person:Person {name:"范闲"}),(person2:Person {name:"北齐圣女海棠朵朵"}) create(person)-[r:Friends]->(person2);
match(person:Person {name:"范闲"}),(person2:Person {name:"庆帝"}) create(person)-[r:Father]->(person2);
match(person:Person {name:"范闲"}),(person2:Person {name:"长公主"}) create(person)-[r:Wife_Mother]->(person2);
match(person:Person {name:"庆帝"}),(person2:Person {name:"二皇子"}) create(person)-[r:Son]->(person2);
match(person:Person {name:"庆帝"}),(person2:Person {name:"长公主"}) create(person)-[r:BrotherSister]->(person2);
match(person:Person {name:"二皇子"}),(person2:Person {name:"靖王世子"}) create(person)-[r:Friends]->(person2);
match(person:Person {name:"北齐圣女海棠朵朵"}),(person2:Person {name:"北齐小皇帝战豆豆"}) create(person)-[r:Friends]->(person2);
match(person:Person {name:"林婉儿"}),(person2:Person {name:"叶灵儿"}) create(person)-[r:Friends]->(person2);
match(person:Person {name:"林婉儿"}),(person2:Person {name:"宰相林若甫"}) create(person)-[r:Father]->(person2);
match(person:Person {name:"林婉儿"}),(person2:Person {name:"长公主"}) create(person)-[r:Mother]->(person2);
match(person:Person {name:"长公主"}),(person2:Person {name:"九品射手燕小乙"}) create(person)-[r:Friends]->(person2);


如果关系建立错了  可以删除关系 
match p = (:Person {name:"林婉儿"})-[r:Couple]-(:Person)  delete r 

考虑了关系的方向
match data=(na:Person {name:"范闲"})-[*2..2]->(nb:Person) return nb

不考虑关系的方向 
match data=(na:Person {name:"范闲"})-[*2..2]-(nb:Person) return nb


shortestPath函数返回路径长度最短的path
MATCH p=shortestPath( (person:Person {name:"王启年"})-[*]-(person2:Person {name:"九品射手燕小乙"}) ) 
   RETURN length(p), nodes(p)

不使用 shortestPath
MATCH p=(person:Person {name:"王启年"})-[*]-(person2:Person {name:"九品射手燕小乙"}) 
   RETURN p 
MATCH p=shortestPath( (person:Person {name:"王启年"})-[*]-(person2:Person {name:"九品射手燕小乙"}) ) 
   where length(p)>1  RETURN p

explain MATCH p=shortestPath( (person:Person {name:"王启年"})-[*]-(person2:Person {name:"九品射手燕小乙"}) ) 
   where length(p)>1  RETURN p

explain MATCH p=shortestPath( (person:Person {name:"王启年"})-[*]-(person2:Person {name:"九品射手燕小乙"}) ) 
   with p where length(p)>1  RETURN p

全文索引 
CALL db.index.fulltext.createNodeIndex("nameAndDescription",["Person"],["name", "description"])
CALL db.index.fulltext.queryNodes("nameAndDescription", "范闲") YIELD node, score
RETURN node.title, node.description, score

