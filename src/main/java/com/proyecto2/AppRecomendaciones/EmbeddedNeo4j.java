package com.proyecto2.AppRecomendaciones;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import org.neo4j.driver.Result;
import org.neo4j.driver.Record;
import java.util.List;
import java.util.ArrayList;

public class EmbeddedNeo4j implements AutoCloseable {
    private final Driver driver;

    public EmbeddedNeo4j(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    @Override
    public void close() {
        driver.close();
    }

    public List<String> recomendarAgentesPorRol(String rol) {
        List<String> agentes = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (p:Personaje)-[:ES_UN]->(:Rol {nombre: $rol}) RETURN p.nombre AS nombre";
            Result result = session.run(query, org.neo4j.driver.Values.parameters("rol", rol));
            while (result.hasNext()) {
                Record record = result.next();
                agentes.add(record.get("nombre").asString());
            }
        }
        return agentes;
    }

    public List<String> recomendarAgentesPorHabilidad(String habilidad) {
        List<String> agentes = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (p:Personaje)-[:TIENE_HABILIDAD]->(:Habilidad {nombre: $habilidad}) RETURN p.nombre AS nombre";
            Result result = session.run(query, org.neo4j.driver.Values.parameters("habilidad", habilidad));
            while (result.hasNext()) {
                Record record = result.next();
                agentes.add(record.get("nombre").asString());
            }
        }
        return agentes;
    }

    public List<String> recomendarUsuariosPorPersonajesEnComun(String usuario) {
        List<String> usuarios = new ArrayList<>();
        try (Session session = driver.session()) {
            String query = "MATCH (u1:Usuario {nombre: $usuario})-[:USA]->(p:Personaje)<-[:USA]-(u2:Usuario) " +
                           "WHERE u1 <> u2 RETURN DISTINCT u2.nombre AS nombre";
            Result result = session.run(query, org.neo4j.driver.Values.parameters("usuario", usuario));
            while (result.hasNext()) {
                Record record = result.next();
                usuarios.add(record.get("nombre").asString());
            }
        }
        return usuarios;
    }
}


