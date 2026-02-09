-- =====================================================
-- BASE DE DATOS: Sistema de Artículos Fintech
-- =====================================================

CREATE DATABASE IF NOT EXISTS articulos_fintech;
USE articulos_fintech;

-- =====================================================
-- TABLA: busquedas
-- Almacena información de las búsquedas realizadas
-- =====================================================
CREATE TABLE busquedas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estudiante VARCHAR(200) NOT NULL,
    base_datos VARCHAR(100) NOT NULL,
    cadena_busqueda TEXT NOT NULL,
    cantidad_documentos INT NOT NULL,
    fecha_busqueda TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================================
-- TABLA: articulos
-- Almacena información completa de los artículos
-- =====================================================
CREATE TABLE articulos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    busqueda_id INT NOT NULL,
    titulo TEXT NOT NULL,
    autores TEXT NOT NULL,
    anio_publicacion INT NOT NULL,
    fuente TEXT NOT NULL,
    doi VARCHAR(200),
    resumen TEXT,
    palabras_clave TEXT,
    cita_apa TEXT,
    FOREIGN KEY (busqueda_id) REFERENCES busquedas(id) ON DELETE CASCADE
);

-- =====================================================
-- TABLA: estrategias_inversion (CRUD)
-- Estrategias de inversión basadas en IA/Fintech
-- =====================================================
CREATE TABLE estrategias_inversion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT NOT NULL,
    tipo_estrategia VARCHAR(100) NOT NULL,
    nivel_riesgo ENUM('Bajo', 'Medio', 'Alto') NOT NULL,
    tecnologias_utilizadas TEXT,
    retorno_esperado DECIMAL(5,2),
    articulo_relacionado_id INT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (articulo_relacionado_id) REFERENCES articulos(id) ON DELETE SET NULL
);

-- =====================================================
-- DATOS INICIALES - BÚSQUEDAS
-- =====================================================
INSERT INTO busquedas (nombre_estudiante, base_datos, cadena_busqueda, cantidad_documentos) VALUES
('Julian David Cristancho Niño', 'Scopus', 'TITLE-ABS-KEY ( ( "financial" AND "simulation" ) AND ( "python" ) )', 121),
('Mariana Alejandra Gordillo Meneses', 'Scopus', 'TITLE-ABS-KEY ( ( "financial simulation" OR "financial modeling" OR "computational finance" ) AND ( "deep learning" OR "artificial intelligence" OR AI OR "risk*" ) AND NOT ( biomedical OR "power market" OR "agriculture" OR "healthcare" OR "farm" OR "sustainability " ) )', 867),
('Ana Sofia Fajardo Leal', 'Scopus', 'TITLE-ABS-KEY ( ( "financial technology" OR FinTech OR "banking systems" ) AND ( "machine learning" OR "data analysis" OR "fraud detection" ) )', 1956);

-- =====================================================
-- DATOS INICIALES - ARTÍCULOS
-- =====================================================

-- Artículos de Julián (Búsqueda ID: 1)
INSERT INTO articulos (busqueda_id, titulo, autores, anio_publicacion, fuente, doi, resumen, palabras_clave, cita_apa) VALUES
(1, 'Investment Decision-Making in the AI Era', 
'Gao Weizheng, Gao Shanzhen, Allagan Julian, Su Jianning, McClinton Brooke, Eyob Ephrem, Strevel Hank B.', 
2026, 
'Communications in Computer and Information Science (CCIS) (Vol. 2722, pp. 499–511). Springer.', 
'https://doi.org/10.1007/978-3-032-12930-7_35',
'This paper explores an interdisciplinary approach to investment decision-making by integrating Python programming, logical frameworks, mathematical models, and algorithmic strategies. Effective investment strategies in a data-driven financial landscape require computational efficiency, predictive modeling, and risk assessment. Python facilitates data processing and simulation, while mathematical models support portfolio optimization and risk management. Logical frameworks ensure consistency in decision rules, and advanced algorithms, including machine learning and dynamic programming, enhance adaptability to market changes.',
'AI in finance; algorithmic trading; decision-making frameworks; financial modeling; Investment strategies; mathematical finance; Python',
'Gao, W., Gao, S., Allagan, J., Su, J., McClinton, B., Eyob, E., & Strevel, H. B. (2026). Investment decision-making in the AI era. In H. R. Arabnia, L. Deligiannidis, S. Amirian, F. Ghareh Mohammadi, & F. Shenavarmasouleh (Eds.), Communications in Computer and Information Science (CCIS) (Vol. 2722, pp. 499–511). Springer. https://doi.org/10.1007/978-3-032-12930-7_35');

INSERT INTO articulos (busqueda_id, titulo, autores, anio_publicacion, fuente, doi, resumen, palabras_clave, cita_apa) VALUES
(1, 'Creating a Python platform for students to learn full-stack development of financial applications',
'Tang, Song',
2026,
'Financial Innovation, 12(1), Article 32.',
'https://doi.org/10.1186/s40854-025-00813-9',
'In finance, programming is helpful in various situations, such as asset pricing, electronic trading, and portfolio management. Python is one of the most popular and widely used programming languages for developing financial applications. In addition, many financial applications are designed according to software development methodologies unique to the financial industry. However, there is no Python platform for students to learn how to use software development methodologies for the full-stack development of financial applications. Therefore, we developed a Python application called the FRE Platform.',
'Client/server architecture; Database; Financial modeling; Full-stack development; GitHub; K|V methodology; Market data feed; MVC design pattern; Python; Software development methodology',
'Tang, S. (2026). Creating a Python platform for students to learn full-stack development of financial applications. Financial Innovation, 12(1), Article 32. https://doi.org/10.1186/s40854-025-00813-9');

INSERT INTO articulos (busqueda_id, titulo, autores, anio_publicacion, fuente, doi, resumen, palabras_clave, cita_apa) VALUES
(1, 'Financial Data Science with Python: An Integrated Approach to Analysis, Modeling, and Machine Learning',
'Chen Haojun',
2025,
'Business Expert Press.',
'https://doi.org/10.4128/9781637428269',
'In today''s finance industry, data-driven decision making is essential. Financial Data Science with Python: An Integrated Approach to Analysis, Modeling, and Machine Learning bridges the gap between traditional finance and modern data science, offering a comprehensive guide for students, analysts, and professionals. This book equips readers with the tools to analyze complex financial data, build predictive models, and apply machine learning techniques to real-world financial challenges.',
'Commerce; Engineering education; Finance; Forecasting; Learning algorithms; Machine learning; Python; Time series analysis; Financial data; Real-world',
'Chen, H. (2025). Financial data science with Python: An integrated approach to analysis, modeling, and machine learning. Business Expert Press. https://doi.org/10.4128/9781637428269');

-- Artículos de Mariana (Búsqueda ID: 2)
INSERT INTO articulos (busqueda_id, titulo, autores, anio_publicacion, fuente, doi, resumen, palabras_clave, cita_apa) VALUES
(2, 'Graph attention-based heterogeneous multi-agent deep reinforcement learning for adaptive portfolio optimization',
'Zhang Bing',
2026,
'Scientific Reports, 16(1), Article 2674.',
'https://doi.org/10.1038/s41598-025-32408-w',
'Traditional portfolio optimization methods face significant limitations in capturing complex asset relationships and adapting to dynamic market conditions. This paper proposes a novel graph attention-based heterogeneous multi-agent deep reinforcement learning framework that addresses these challenges through innovative integration of graph neural networks and specialized agent architectures. The framework employs graph attention networks to model time-varying asset correlations and dependencies.',
'Adaptive optimization; Deep reinforcement learning; Financial markets; Graph attention networks; Multi-agent systems; Portfolio optimization',
'Zhang, B. (2026). Graph attention-based heterogeneous multi-agent deep reinforcement learning for adaptive portfolio optimization. Scientific Reports, 16(1), Article 2674. https://doi.org/10.1038/s41598-025-32408-w');

INSERT INTO articulos (busqueda_id, titulo, autores, anio_publicacion, fuente, doi, resumen, palabras_clave, cita_apa) VALUES
(2, 'Integrating AI and Social Responsibility in Portfolio Optimization: A Study with Evolutionary Algorithms',
'Yu Hanbo, Ding Steven H. H.',
2026,
'Lecture Notes of the Institute for Computer Sciences, Social-Informatics and Telecommunications Engineering (LNICST) (Vol. 654, pp. 219–230). Springer.',
'https://doi.org/10.1007/978-3-032-05832-4_12',
'In today''s complex financial markets, the need for advanced computational methods in portfolio optimization is critical for keeping economic stability and enhancing social well-being. Traditional approaches, such as mean-variance optimization in Modern Portfolio Theory (MPT), often struggle to handle the non-linearities and volatility presented by modern assets. With the development of technology, artificial intelligence is now capable of contributing to sophisticated financial tools.',
'AI and Social Impact; Computational Finance; Evolutionary Algorithm; Optimization; Portfolio Management',
'Yu, H., & Ding, S. H. H. (2026). Integrating AI and social responsibility in portfolio optimization: A study with evolutionary algorithms. In C. Fachkha, B. C. M. Fung, & F. Tchakounté (Eds.), Lecture Notes of the Institute for Computer Sciences, Social-Informatics and Telecommunications Engineering (LNICST) (Vol. 654, pp. 219–230). Springer. https://doi.org/10.1007/978-3-032-05832-4_12');

INSERT INTO articulos (busqueda_id, titulo, autores, anio_publicacion, fuente, doi, resumen, palabras_clave, cita_apa) VALUES
(2, 'Tail-Diffusion: Simulation Scenarios in Finance with Denoising Diffusion Models and Tail Risk Analysis',
'Thu Nguyen Minha, Ngia Huynh Hoang Trunga, Huy Tran Hong, Thinh Vu Ducd, Man Ngo Minh',
2026,
'Communications in Computer and Information Science (CCIS) (Vol. 2586, pp. 171–183). Springer.',
'https://doi.org/10.1007/978-3-031-98167-8_13',
'This study explores the application of generative AI, particularly Denoising Diffusion Probabilistic Model (DDPM), for financial scenario simulation. To address the limitations of existing models, this study introduces Tail-Diffusion, a novel framework that incorporates tail-risk metrics such as Value at Risk (VaR) and Expected Shortfall (ES) to capture extreme market conditions.',
'Diffusion; Tail-risk; Time series; De-noising; Diffusion model; Financial data; Probabilistic models; Risk analyze; Scenario simulations',
'Thu, N. M., Ngia, H. H. T., Huy, T. H., Thinh, V. D., & Man, N. M. (2026). Tail-diffusion: Simulation scenarios in finance with denoising diffusion models and tail risk analysis. In N. T. Nguyen, V. H. Pham, T. D. Tran, P. T. Tin, T.-P. Hong, Y. Manolopoulos, & N. A. Le Khac (Eds.), Communications in Computer and Information Science (CCIS) (Vol. 2586, pp. 171–183). Springer. https://doi.org/10.1007/978-3-031-98167-8_13');

-- Artículos de Ana Sofía (Búsqueda ID: 3)
INSERT INTO articulos (busqueda_id, titulo, autores, anio_publicacion, fuente, doi, resumen, palabras_clave, cita_apa) VALUES
(3, 'A systematic review of AI-driven zero-carbon business models in the financial sector',
'Dias, S. N. R. Fiona; Tharanga B.B.; Dewasiri, Narayanage Jayantha',
2026,
'Discover Sustainability, 7(1), Article 30.',
'https://doi.org/10.1007/s43621-025-02298-z',
'The transition to a zero-carbon economy has become a priority for the financial sector in the context of the intensifying global climate crisis and the increasing pressure from regulatory and stakeholder influence. Artificial Intelligence (AI) has become a powerful enabler in transforming the traditional financial sector by offering influential technologies to drive towards sustainability by eliminating carbon emissions.',
'Artificial Intelligence (AI); Financial sector; PRISMA; Sustainable business models; Systematic literature review; Zero-carbon',
'Dias, S. N. R. F., Tharanga, B. B., & Dewasiri, N. J. (2026). A systematic review of AI-driven zero-carbon business models in the financial sector. Discover Sustainability, 7(1), Article 30. https://doi.org/10.1007/s43621-025-02298-z');

INSERT INTO articulos (busqueda_id, titulo, autores, anio_publicacion, fuente, doi, resumen, palabras_clave, cita_apa) VALUES
(3, 'Performance Analysis of Cost-Sensitive Oversampling Mechanisms for Credit Card Fraud Detection',
'Ileberi, Emmanuel; Sun, Yanxia',
2025,
'IEEE Access, 13, 202655–202677.',
'https://doi.org/10.1109/ACCESS.2025.3637132',
'Credit card fraud detection presents a persistent challenge due to severe class imbalance and the high costs associated with undetected fraudulent transactions. While oversampling techniques such as Synthetic Minority Oversampling Technique (SMOTE) and Adaptive Synthetic Sampling Approach for Imbalanced Learning (ADASYN) have improved minority class detection, most methods are cost-agnostic and do not account for the financial impact of individual misclassifications.',
'Artificial intelligence; Credit card fraud detection; Cost-sensitive learning; Imbalanced classification; Oversampling techniques; SMOTE; ADASYN; Logistic regression; Random forests',
'Ileberi, E., & Sun, Y. (2025). Performance analysis of cost-sensitive oversampling mechanisms for credit card fraud detection. IEEE Access, 13, 202655–202677. https://doi.org/10.1109/ACCESS.2025.3637132');

INSERT INTO articulos (busqueda_id, titulo, autores, anio_publicacion, fuente, doi, resumen, palabras_clave, cita_apa) VALUES
(3, 'The Role of Financial Development and Financial Technology in Driving Renewable Energy Technologies',
'Aghaei, Majid; Rezagholizadeh, Mahdieh',
2026,
'Journal of Renewable Energy and Environment',
'https://doi.org/10.1007/s43621-025-02298-z',
'The transition to renewable energy technologies (RETs) is crucial for mitigating greenhouse gas emissions and advancing sustainable development. This study investigates the role of financial development (FD) and financial technology (FinTech) in fostering RET deployment across developed and developing economies, while also assessing the moderating effect of FinTech on the FD–RET nexus.',
'Developed and Developing Countries; Financial Development; Financial Technology; Renewable Energy Technology',
'Shahbaz, M., Shahzad, S. J. H., Mahalik, M. K., & Hammoudeh, S. (2025). The role of financial development and financial technology in driving renewable energy technologies: Evidence from developed and developing countries. Environmental Science and Pollution Research, 32(4), 10145–10162. https://doi.org/10.1007/s43621-025-02298-z');

-- =====================================================
-- DATOS EJEMPLO - ESTRATEGIAS DE INVERSIÓN
-- =====================================================
INSERT INTO estrategias_inversion (nombre, descripcion, tipo_estrategia, nivel_riesgo, tecnologias_utilizadas, retorno_esperado, articulo_relacionado_id) VALUES
('Optimización de Portafolio con Deep Learning',
'Estrategia que utiliza redes neuronales profundas y algoritmos de aprendizaje por refuerzo para optimizar la asignación de activos en un portafolio, adaptándose dinámicamente a las condiciones del mercado.',
'IA Avanzada',
'Alto',
'Python, TensorFlow, Deep Reinforcement Learning, Graph Neural Networks',
16.80,
4),

('Trading Algorítmico con Python',
'Sistema automatizado de trading que emplea modelos predictivos y simulaciones financieras para la toma de decisiones de inversión en tiempo real.',
'Algoritmos Predictivos',
'Medio',
'Python, Machine Learning, Backtesting, API Trading',
12.50,
1),

('Detección de Fraude con SMOTE',
'Sistema de prevención de fraude financiero que utiliza técnicas de oversampling cost-sensitive para identificar transacciones fraudulentas con alta precisión.',
'IA para Seguridad',
'Bajo',
'Python, SMOTE, ADASYN, Random Forest, XGBoost',
8.30,
8);
