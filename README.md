# REST Assured API Automation Project

![Java](https://img.shields.io/badge/Java-21-blue)
![Maven](https://img.shields.io/badge/Maven-3.9.0-green)
![JUnit5](https://img.shields.io/badge/JUnit-5.10.0-red)
![REST Assured](https://img.shields.io/badge/REST_Assured-4.6.1-orange)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ-IDEA-purple)

---
## Description

Ce projet est un **framework d'automatisation pour les tests d'API REST** utilisant Rest Assured. Il permet de :
- envoyer des requêtes HTTP : GET, POST, PUT, DELETE
- valider les réponses et assertion JSON
- gérer les headers, pathParams et bodies Json
- Extraire facilemnt des données de réponse
- Intéger les test dans une pipeline CI/CD

🔹Qu'est ce que Rest Assured ? 

Rest Assured est une bibliothéque Java open-source pour automatiser les tests d'API REST.

Elle permet de :
- Créer et envoyer rapidement des requêtes HTTP
- Vérifier les réponses avec des assertions Hamcrest
- Appliquer le style BDD : given() / when() / then()
- Gérer facilement headers, pathParams, queryParams et bodies JSON
- S’intégrer avec JUnit, TestNG et Maven CI/CD

💡 Pourquoi l’utiliser ?

Pour écrire des tests lisibles, maintenables et robustes, avec un minimum de complexité.

---

# 🧰 **Technologies Utilisées**

| Technologie           | Rôle | 
|-----------------------|------|
| **Java 17**           | Langage principal |
| **Maven**             | Gestion des dépendances / Build | 
| **Rest Assured 5.x**  | Test API REST |
| **TestNG**            | Framework de tests |
| **Hamcrest Matchers** | Assertions avancées |
| **JSONPath**          | Extraction de données JSON |
| **IntelliJ IDEA**     | IDE recommandé |

---

📥 Installation

1. Clonez le projet :  
<pre>
git clone https://github.com/votre-utilisateur/restassured-api-automation.git
</pre>

2. Installez les dépendances :  
<pre>
mvn clean install
</pre>
---
▶️ Exécution des tests

h3. Depuis Maven
<pre>
mvn test
</pre>

h3. Depuis IntelliJ IDEA
1. Ouvrez le fichier AutomateGet.java ou AutomateDelete.java  
2. Clic droit → Run Test
---

🔧 Configuration Commune

<pre>
RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
    .setBaseUri("https://api.postman.com")
    .addHeader("X-API-Key", "VOTRE_API_KEY")
    .setContentType(ContentType.JSON)
    .log(LogDetail.ALL);

RestAssured.requestSpecification = requestSpecBuilder.build();

ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
    .expectStatusCode(200)
    .expectContentType(ContentType.JSON)
    .log(LogDetail.ALL);
</pre>
-----

🧪 Exemples de tests

✅Test GET – Liste des workspaces
<pre>
@Test
public void validate_get_status_code() {
    given()
        .baseUri("https://api.postman.com")
        .header("X-API-Key", "API_KEY")
    .when()
        .get("/workspaces")
    .then()
        .log().all()
        .statusCode(200)
        .body("workspaces.size()", equalTo(3));
}
</pre>

✅ Test DELETE – Suppression d’un workspace
<pre>
@Test
public void validate_delete_request_bdd_style() {
    String workspaceId = "b0507622-8e2b-4129-8a38-0d9a1328929f";

    given()
        .pathParam("workspaceId", workspaceId)
    .when()
        .delete("/workspaces/{workspaceId}")
    .then()
        .log().all()
        .body("workspace.id", equalTo(workspaceId));
}
</pre>

✅ Extraction de données JSON
<pre>
String response = given()
    .baseUri("https://api.postman.com")
    .header("X-API-Key", "API_KEY")
.when()
    .get("/workspaces")
.then()
    .statusCode(200)
    .extract()
    .asString();

System.out.println("Workspace Name = " + JsonPath.from(response).getString("workspaces[0].name"));
</pre>
---
 📊 Rapports

Les tests JUnit ou TestNG génèrent automatiquement des rapports HTML :  
<pre>
target/surefire-reports/index.html
</pre>

Ces rapports contiennent :  
* Statut des tests  
* Temps d’exécution  
* StackTrace en cas d’échec  
* Logs détaillés Rest Assured
  
---
🤝 Contribution

1. Forkez le repository  
2. Créez votre branche :  
<pre>
git checkout -b feature/nouvelle-feature
</pre>
3. Committez vos changements :  
<pre>
git commit -m "Ajout d’un nouveau test API"
</pre>
4. Poussez votre branche :  
<pre>
git push origin feature/nouvelle-feature
</pre>
5. Ouvrez une Pull Request

   
 ------

👩‍💻 Auteur
Rahma Louati |Software QA Engineer – Test manuel & automatisé|




Ouvrez une Pull Request
