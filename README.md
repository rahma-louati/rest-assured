h1. REST Assured API Automation Project

[!https://img.shields.io/badge/Java-21-lightgrey?style=flat!]
[!https://img.shields.io/badge/Maven-3.9.0-lightgrey?style=flat!]
[!https://img.shields.io/badge/JUnit-5.10.0-lightgrey?style=flat!]
[!https://img.shields.io/badge/REST_Assured-4.6.1-lightgrey?style=flat!]
[!https://img.shields.io/badge/IntelliJ-IDEA-lightgrey?style=flat!]

h2. Objectif du projet

Ce projet a pour but de :  
* Pratiquer l’automatisation des API REST  
* Structurer un framework minimaliste et maintenable  
* Utiliser Rest Assured de manière professionnelle  
* Appliquer le style BDD Fluent  
* Préparer l’intégration future dans CI/CD (GitHub Actions, Jenkins)  

h2. Installation

1. Clonez le projet :  
<pre>
git clone https://github.com/votre-utilisateur/restassured-api-automation.git
</pre>

2. Installez les dépendances :  
<pre>
mvn clean install
</pre>

h2. Exécution des tests

h3. Depuis Maven
<pre>
mvn test
</pre>

h3. Depuis IntelliJ IDEA
1. Ouvrez le fichier AutomateGet.java ou AutomateDelete.java  
2. Clic droit → Run Test

h2. Configuration commune

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

h2. Exemples de tests

h3. Test GET – Liste des workspaces
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

h3. Test DELETE – Suppression d’un workspace
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

h3. Extraction de données JSON
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

h2. Rapports

Les tests JUnit ou TestNG génèrent automatiquement des rapports HTML :  
<pre>
target/surefire-reports/index.html
</pre>

Ces rapports contiennent :  
* Statut des tests  
* Temps d’exécution  
* StackTrace en cas d’échec  
* Logs détaillés Rest Assured  

h2. Contribution

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

h2. Technologies utilisées

[!https://img.shields.io/badge/Java-21-lightgrey?style=flat!]
[!https://img.shields.io/badge/Maven-3.9.0-lightgrey?style=flat!]
[!https://img.shields.io/badge/JUnit-5.10.0-lightgrey?style=flat!]
[!https://img.shields.io/badge/REST_Assured-4.6.1-lightgrey?style=flat!]
[!https://img.shields.io/badge/IntelliJ-IDEA-lightgrey?style=flat!]

* Java 21 : langage principal  
* Maven 3.9.0 : gestion des dépendances et build  
* JUnit 5 : framework de test  
* REST Assured : automatisation des tests d'API  
* JSON : format des requêtes et réponses  
* IntelliJ IDEA : IDE pour le développement et tests




Ouvrez une Pull Request
