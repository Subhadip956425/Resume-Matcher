package com.resumematcher.config;

import com.resumematcher.dto.JdRequestDTO;
import com.resumematcher.repository.JobDescriptionRepository;
import com.resumematcher.service.JdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

    private final JdService jdService;
    private final JobDescriptionRepository jdRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (jdRepository.count() > 0) {
            log.info("JDs already seeded, skipping.");
            return;
        }

        log.info("Seeding sample Job Descriptions...");
        getSampleJds().forEach(jdService::addJd);
        log.info("Seeded {} JDs successfully.", getSampleJds().size());
    }

    private java.util.List<JdRequestDTO> getSampleJds() {
        java.util.List<JdRequestDTO> jds = new java.util.ArrayList<>();

        JdRequestDTO jd1 = new JdRequestDTO();
        jd1.setRole("Scientific Programmer - Riverside Research");
        jd1.setJdText("""
            Position Overview: Riverside Research is seeking a Scientific Programmer/Software Engineer
            to support development of high-performance large scale scientific simulation codes.
            Required Qualifications:
            Applied programming experience with low-level languages like C, C++ and Fortran.
            Applied software development on Linux or Unix-like systems.
            Applied experience coding with Python, Unix shell scripting.
            Experienced with version control software applications Git.
            Experience with parallel programming on high-performance computers MPI and OpenMP.
            Bachelor with 5+ years of experience, or Master with 3+ years of experience.
            Desired Qualifications:
            Experience with full software development lifecycle SDLC.
            Experience developing cross-platform Windows and Linux software.
            Familiarity with linear algebra and numerical solutions.
            Global Comp: $180,000 - $220,000
            """);
        jds.add(jd1);

        JdRequestDTO jd2 = new JdRequestDTO();
        jd2.setRole("Senior Java Developer - Capgemini");
        jd2.setJdText("""
            Job Description:
            Should have 7 years of strong hands-on experience with Core Java Spring Boot.
            Should have 4 years of strong hands-on experience with Angular or React.
            Hands on experience with Relational Database Microsoft SQL Server or No SQL is required.
            Should have strong hands on experience working on Microservices Kafka REST API Development.
            Hands on experience working with containerization technologies Docker Kubernetes.
            Experience in modernizing legacy application to modernized distributed platforms.
            Core Banking or Financial Wealth Management Domain Working Experience Required.
            Good to have:
            Python experience.
            AI ML exposure is valuable.
            Experience in Cloud platforms Azure.
            Experience in DevOps pipeline CI/CD Jenkins.
            The base compensation range: 61087 - 104364
            """);
        jds.add(jd2);

        JdRequestDTO jd3 = new JdRequestDTO();
        jd3.setRole("Software Engineer - Adobe");
        jd3.setJdText("""
            The Opportunity:
            Adobe is seeking talented and passionate Software Engineers to help plan, design, develop,
            and test software systems or applications for software enhancements and new products.
            What You Need To Succeed:
            Bachelor or Master in Computer Science, Computer Engineering, Electrical Engineering.
            5-7+ years of relevant experience.
            Proficient in programming languages such as Python, Java, C++.
            Strong technical background with analytical and problem-solving skills.
            Interest or experience in AI ML concepts with exposure to AI tools is a plus.
            Familiarity with client-side frameworks and libraries like React, Angular, jQuery is a plus.
            Excellent problem-solving and debugging skills and direct experience with DevOps in SaaS environment.
            The US pay range: $139,000 - $257,550 annually.
            """);
        jds.add(jd3);

        JdRequestDTO jd4 = new JdRequestDTO();
        jd4.setRole("Software Engineer - Astra");
        jd4.setJdText("""
            The Opportunity:
            As a Software Engineer at Astra you will build systems that support rocket testing,
            manufacturing and launch operations.
            Why We Value You:
            3+ years of experience in software engineering including production-level Python development.
            Solid understanding of system design, data pipelines and APIs.
            Experience with cloud-native services AWS, Kubernetes, Docker and telemetry or time-series systems.
            Familiarity with REST and gRPC APIs and common data serialization formats JSON, YAML, Protobuf.
            Proven ability to deliver software in high-stakes or cross-functional environments.
            Strong communication skills and a proactive mindset.
            Desired Multipliers:
            Experience in aerospace, robotics or hardware-software systems.
            Exposure to TypeScript, Go or C/C++.
            Open-source contributions or relevant side projects.
            The pay range: $130,000 - $160,000 per year.
            """);
        jds.add(jd4);

        JdRequestDTO jd5 = new JdRequestDTO();
        jd5.setRole("Software Engineer - Lockheed Martin");
        jd5.setJdText("""
            Basic Qualifications:
            Programming experience required.
            Be able to understand SV CONOPS.
            Be able to troubleshoot issues with minimum supervision.
            Be able to collaborate with others.
            This position is for a Software Engineer supporting the Space Software Product Line.
            Desired Skills:
            Python, Unit testing, Agile programming, Full stack development.
            Software testing, VM Infrastructure.
            STARS EASY framework experience.
            """);
        jds.add(jd5);

        JdRequestDTO jd6 = new JdRequestDTO();
        jd6.setRole("Software Engineer - Applied Materials");
        jd6.setJdText("""
            Role Responsibilities:
            Design and develop code for large scale complex software solutions for semiconductor equipment.
            Lead and execute complex software projects.
            Define software specifications and test plans.
            Communicate with internal and external customers for requirement analysis.
            Troubleshoot a wide range of complex software problems.
            Minimum Qualifications:
            Bachelor degree in Computer Science, Electrical Engineering, Mathematics, Physics.
            7 to 10 years of related experience.
            Extensive experience with controlling instrument equipment requiring high precision.
            Preferred Qualifications:
            PhD degree in a related field.
            Proficient in Modern C++, Python, C#, Java, Rust, machine learning, web service architecture.
            Signal processing, computer vision, robotics, user interface design, mathematical modelling.
            Extensive experience with development on embedded system and PyTorch.
            Experience with FPGA is preferred.
            Salary: $176,000.00 - $242,000.00
            """);
        jds.add(jd6);

        JdRequestDTO jd7 = new JdRequestDTO();
        jd7.setRole("Software Engineer Infrastructure - Meta");
        jd7.setJdText("""
            Software Engineer Infrastructure Responsibilities:
            Collaborate with cross-functional teams product, design, operations, infrastructure.
            Implement custom user interfaces using latest programming techniques and technologies.
            Develop reusable software components for interfacing with back-end platforms.
            Analyze and optimize code for quality, efficiency and performance.
            Lead complex technical or product efforts and provide technical guidance to peers.
            Architect efficient and scalable systems that drive complex applications.
            Minimum Qualifications:
            Bachelor degree in Computer Science, Computer Engineering or relevant technical field.
            2+ years of programming experience in a relevant language.
            Experience building maintainable and testable code bases including API design and unit testing.
            Preferred Qualifications:
            Experience in programming languages such as C, C++, Java.
            Exposure to architectural patterns of large scale software applications.
            Experience with scripting languages such as Python, JavaScript.
            2+ years of relevant experience building large-scale infrastructure applications.
            Compensation: $58.65/hour to $181,000/year + bonus + equity + benefits.
            """);
        jds.add(jd7);

        JdRequestDTO jd8 = new JdRequestDTO();
        jd8.setRole("Software Developer - Accenture Federal Services");
        jd8.setJdText("""
            Job Description:
            Software application developer and engineer supporting the design, development, integration,
            maintenance and enhancement of custom systems.
            Must have:
            Experience with software development.
            Bachelors degree in Computer Science or related field.
            Security Clearance: Active TS/SCI with polygraph clearance.
            The pay range for California, Colorado, Hawaii, Illinois, Maryland, Massachusetts,
            Minnesota, New Jersey, New York, Washington, Vermont: $75,500 - $131,200 USD.
            """);
        jds.add(jd8);

        return jds;
    }
}
