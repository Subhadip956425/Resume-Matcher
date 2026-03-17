package com.resumematcher.data;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SkillsDictionary {

    // list of tech skills
    private static final List<String> SKILLS = Arrays.asList(
        // Languages
        "Java", "Python", "JavaScript", "TypeScript", "C", "C++", "C#",
        "Go", "Rust", "Kotlin", "Swift", "PHP", "Ruby", "Scala", "R",
        "MATLAB", "Perl", "Shell", "Bash", "PowerShell", "Groovy", "Fortran",
        "Haskell", "Lua", "Dart", "Objective-C", "Assembly",

        // Frontend
        "React", "Angular", "Vue", "Next.js", "Nuxt.js", "Redux",
        "HTML", "CSS", "SCSS", "SASS", "Tailwind", "Bootstrap",
        "jQuery", "Webpack", "Vite", "Babel", "Three.js", "D3.js",
        "Material UI", "Ant Design", "Chakra UI", "Storybook",

        // Backend / Frameworks
        "Spring Boot", "Spring", "Spring MVC", "Spring Security",
        "Spring Cloud", "Hibernate", "JPA", "Node.js", "Express",
        "Django", "Flask", "FastAPI", "Laravel", "Rails",
        "ASP.NET", ".NET", "NestJS", "Quarkus", "Micronaut",
        "Struts", "JSF", "Servlet",

        // Databases
        "MySQL", "PostgreSQL", "MongoDB", "Oracle", "SQL Server",
        "SQLite", "Redis", "Cassandra", "DynamoDB", "Elasticsearch",
        "Neo4j", "CouchDB", "Firebase", "Supabase", "MariaDB",
        "DB2", "H2", "InfluxDB", "Snowflake", "BigQuery",

        // Cloud & DevOps
        "AWS", "Azure", "GCP", "Google Cloud", "Docker", "Kubernetes",
        "Helm", "Terraform", "Ansible", "Jenkins", "GitHub Actions",
        "GitLab CI", "CircleCI", "Travis CI", "ArgoCD", "Prometheus",
        "Grafana", "ELK Stack", "Logstash", "Kibana", "Nginx", "Apache",

        // Messaging & Streaming
        "Kafka", "RabbitMQ", "ActiveMQ", "SQS", "SNS", "Pub/Sub",
        "gRPC", "WebSocket", "SignalR",

        // APIs & Protocols
        "REST", "REST API", "GraphQL", "SOAP", "OpenAPI", "Swagger",
        "OAuth", "OAuth2", "JWT", "SAML", "API Gateway",

        // AI / ML
        "Machine Learning", "Deep Learning", "NLP", "TensorFlow",
        "PyTorch", "Keras", "scikit-learn", "OpenCV", "Pandas",
        "NumPy", "Matplotlib", "Hugging Face", "LangChain",
        "BERT", "GPT", "Computer Vision", "Data Science",

        // Testing
        "JUnit", "Mockito", "Selenium", "Cypress", "Jest",
        "Pytest", "Mocha", "Chai", "TestNG", "Postman",
        "SonarQube", "Jacoco",

        // Version Control & Collaboration
        "Git", "GitHub", "GitLab", "Bitbucket", "SVN", "Jira",
        "Confluence", "Trello", "Figma", "Notion",

        // Architecture & Patterns
        "Microservices", "Monolithic", "Event-Driven",
        "Domain-Driven Design", "DDD", "CQRS", "Hexagonal Architecture",
        "MVC", "SOLID", "Design Patterns",

        // Mobile
        "Android", "iOS", "React Native", "Flutter", "Ionic",
        "Xamarin", "Expo",

        // Other
        "Linux", "Unix", "Windows Server", "Agile", "Scrum",
        "Kanban", "CI/CD", "DevOps", "SDLC", "TDD", "BDD",
        "OpenMP", "MPI", "CUDA", "FPGA", "Embedded", "IoT",
        "Blockchain", "Solidity", "Web3", "Protobuf", "YAML",
        "JSON", "XML", "gRPC", "WebRTC"
    );

    public List<String> getAllSkills() {
        return SKILLS;
    }
}
