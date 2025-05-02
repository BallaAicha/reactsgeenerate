

package com.sgab.projectgenerator.service;

import com.sgab.projectgenerator.model.ProjectRequest;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    public String getPackageJsonTemplate(ProjectRequest request) {
        return String.format("""
            {
              "name": "%s",
              "private": true,
              "version": "%s",
              "type": "module",
              "scripts": {
                "dev": "vite",
                "build": "vite build",
                "preview": "vite preview"
              },
              "dependencies": {
                "react": "^18.2.0",
                "react-dom": "^18.2.0"%s%s%s
              },
              "devDependencies": {
                "@vitejs/plugin-react": "^4.0.0",
                "vite": "^4.3.9"%s
              }
            }
            """,
                request.getProjectName(),
                request.getVersion(),
                request.getFeatures().isRouting() ? ",\n    \"react-router-dom\": \"^6.11.2\"" : "",
                request.getFeatures().isI18n() ? ",\n    \"i18next\": \"^22.5.0\",\n    \"react-i18next\": \"^12.3.1\"" : "",
                request.getFeatures().isApiClient() ? ",\n    \"axios\": \"^1.4.0\"" : "",
                request.getLanguage().equals("typescript") ? ",\n    \"typescript\": \"^5.0.2\",\n    \"@types/react\": \"^18.2.8\",\n    \"@types/react-dom\": \"^18.2.4\"" : ""
        );
    }

    public String getViteConfigTemplate(ProjectRequest request) {
        if (request.getLanguage().equals("typescript")) {
            return """
                import { defineConfig } from 'vite';
                import react from '@vitejs/plugin-react';

                export default defineConfig({
                  plugins: [react()],
                  server: {
                    port: 3000
                  }
                });
                """;
        } else {
            return """
                import { defineConfig } from 'vite';
                import react from '@vitejs/plugin-react';

                export default defineConfig({
                  plugins: [react()],
                  server: {
                    port: 3000
                  }
                });
                """;
        }
    }

    public String getIndexHtmlTemplate(ProjectRequest request) {
        return String.format("""
            <!DOCTYPE html>
            <html lang="en">
              <head>
                <meta charset="UTF-8" />
                <link rel="icon" type="image/svg+xml" href="/vite.svg" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <title>%s</title>
              </head>
              <body>
                <div id="root"></div>
                <script type="module" src="/src/main.%s"></script>
              </body>
            </html>
            """,
                request.getProjectName(),
                request.getLanguage().equals("typescript") ? "tsx" : "jsx"
        );
    }

    public String getMainTemplate(ProjectRequest request) {
        String ext = request.getLanguage().equals("typescript") ? "tsx" : "jsx";
        return String.format("""
            import React from 'react';
            import ReactDOM from 'react-dom/client';
            import App from './App.%s';
            import './index.css';
            
            ReactDOM.createRoot(document.getElementById('root')%s).render(
              <React.StrictMode>
                <App />
              </React.StrictMode>
            );
            """,
                ext,
                request.getLanguage().equals("typescript") ? " as HTMLElement" : ""
        );
    }

//    public String getAppTemplate(ProjectRequest request) {
//        return """
//            import React from 'react';
//
//            function App() {
//              return (
//                <div className="App">
//                  <h1>Welcome to Your React App</h1>
//                </div>
//              );
//            }
//
//            export default App;
//            """;
//    }


    public String getAppTemplate(ProjectRequest request) {
        String ext = request.getLanguage().equals("typescript") ? "tsx" : "jsx";

        return """
        import React from 'react';
        
                function App() {
                  return (
                      <div style={styles.pageContainer}>
                        <div style={styles.mainCard}>
                          <div style={styles.header}>
                            <div style={styles.emojiContainer}>
                              <div style={styles.emoji}>📁</div>
                            </div>
                            <h1 style={styles.title}>Bienvenue au Centre d'Expertise SGABS</h1>
                            <p style={styles.subtitle}>
                              Projet généré par le SGABS Project Generator
                            </p>
                          </div>
                
                          <div style={styles.content}>
                            <div style={styles.section}>
                              <h2 style={styles.sectionTitle}>
                                👋 Bonjour et bienvenue dans votre projet!
                              </h2>
                              <p style={styles.paragraph}>
                                Ce projet a été créé selon les standards et meilleures pratiques
                                de développement front-end utilisés chez SGABS.
                              </p>
                              <p style={styles.paragraph}>
                                Vous pouvez personnaliser cette application selon vos besoins.
                              </p>
                            </div>
                
                            <div style={styles.gridContainer}>
                              <div style={styles.featureCardBlue}>
                                <div style={styles.featureEmoji}>🧩</div>
                                <h3 style={styles.featureTitle}>Technologies</h3>
                                <p style={styles.featureText}>
                                  Projet basé sur React avec TypeScript,
                                  Vite, et Tailwind CSS.
                                </p>
                              </div>
                
                              <div style={styles.featureCardIndigo}>
                                <div style={styles.featureEmoji}>✨</div>
                                <h3 style={styles.featureTitle}>Fonctionnalités</h3>
                                <p style={styles.featureText}>
                                  ✅ Routing
                                  ✅ Internationalisation
                                  ✅ API Client
                                  ✅ Configuration multi-tenant
                                </p>
                              </div>
                            </div>
                
                            <div style={styles.section}>
                              <h3 style={styles.sectionTitle}>
                                📂 Structure du Projet SGABS
                              </h3>
                
                              <div style={styles.structureCard}>
                                <div style={styles.structureGrid}>
                                  <div>
                                    <h4 style={styles.structureSubtitle}>
                                      Organisation des dossiers
                                    </h4>
                                    <ul style={styles.structureList}>
                                      <li style={styles.structureListItem}>
                                        <span style={styles.structureHighlight}>src/assets/</span>
                                        <span>Ressources statiques (images, CSS)</span>
                                      </li>
                                      <li style={styles.structureListItem}>
                                        <span style={styles.structureHighlight}>src/pages/</span>
                                        <span>Pages et composants spécifiques aux pages</span>
                                      </li>
                                      <li style={styles.structureListItem}>
                                        <span style={styles.structureHighlight}>src/config/</span>
                                        <span>Configuration de l'environnement, fichiers de traduction, config multi-tenant</span>
                                      </li>
                                      <li style={styles.structureListItem}>
                                        <span style={styles.structureHighlight}>src/routes/</span>
                                        <span>Gestion des routes et de la navigation</span>
                                      </li>
                                      <li style={styles.structureListItem}>
                                        <span style={styles.structureHighlight}>src/apisClient/</span>
                                        <span>Clients d'API pour les intégrations backend</span>
                                      </li>
                                    </ul>
                                  </div>
                
                                  <div>
                                    <h4 style={styles.structureSubtitle}>
                                      Architecture Feature-Based
                                    </h4>
                                    <ul style={styles.structureList}>
                                      <li style={styles.structureListItem}>
                                        <span style={styles.structureHighlight}>packages/core/</span>
                                        <span>Fonctionnalités métier principales</span>
                                      </li>
                                      <li style={styles.structureListItem}>
                                        <span style={styles.structureHighlight}>feat-*/components/</span>
                                        <span>Composants spécifiques à la fonctionnalité</span>
                                      </li>
                                      <li style={styles.structureListItem}>
                                        <span style={styles.structureHighlight}>feat-*/core/</span>
                                        <span>Logique métier indépendante de l'UI</span>
                                      </li>
                                      <li style={styles.structureListItem}>
                                        <span style={styles.structureHighlight}>feat-*/web/</span>
                                        <span>Composants web et intégration UI</span>
                                      </li>
                                    </ul>
                                  </div>
                                </div>
                              </div>
                            </div>
                
                            <div style={styles.gridContainer}>
                              <div style={styles.commandCard}>
                                <h4 style={styles.cardTitle}>
                                  🛠️ Commandes Utiles
                                </h4>
                                <div style={styles.codeBlock}>
                                  <p style={styles.codeLine}># Installation des dépendances</p>
                                  <p style={{...styles.codeLine, ...styles.codeCommand}}>npm install</p>
                                  <p style={styles.codeLine}># Démarrer le serveur de développement</p>
                                  <p style={{...styles.codeLine, ...styles.codeCommand}}>npm run dev</p>
                                  <p style={styles.codeLine}># Générer une version de production</p>
                                  <p style={{...styles.codeLine, ...styles.codeCommand}}>npm run build</p>
                                </div>
                              </div>
                
                              <div style={styles.bestPracticesCard}>
                                <h4 style={styles.cardTitle}>
                                  🌐 Bonnes Pratiques SGABS
                                </h4>
                                <ul style={styles.bestPracticesList}>
                                  <li>✓ Modularité par fonctionnalité</li>
                                  <li>✓ Séparation logique métier / UI</li>
                                  <li>✓ Composants réutilisables</li>
                                  <li>✓ Architecture scalable</li>
                                  <li>✓ Configuration centralisée</li>
                                  <li>✓ Support multilingue</li>
                                </ul>
                              </div>
                            </div>
                
                            <div style={styles.nextStepsCard}>
                              <h3 style={styles.sectionTitle}>Étapes suivantes</h3>
                              <ul style={styles.nextStepsList}>
                                <li>Consultez le fichier <code style={styles.codeInline}>README.md</code> pour les instructions détaillées</li>
                                <li>Explorez la structure du projet et familiarisez-vous avec les composants</li>
                                <li>Examinez le dossier <code style={styles.codeInline}>packages/core/feat-login</code> pour comprendre l'organisation par fonctionnalité</li>
                                <li>Rejoignez le canal Slack <code style={styles.codeInline}>#sgabs-dev-support</code> pour toute question</li>
                              </ul>
                            </div>
                
                            <div style={styles.buttonContainer}>
                              <a href="https://github.com/sgabs" style={styles.buttonPrimary}>
                                GitHub
                              </a>
                              <a href="#" style={styles.buttonSecondary}>
                                Documentation
                              </a>
                            </div>
                          </div>
                
                          <div style={styles.footer}>
                            <p>© SGABS Centre d'Expertise 2024 | Version 1.0.0</p>
                          </div>
                        </div>
                      </div>
                  );
                }
                
                const styles = {
                  pageContainer: {
                    minHeight: '100vh',
                    background: 'linear-gradient(to bottom, #f0f9ff, #e0e7ff)',
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    padding: '24px',
                    textAlign: 'center'
                  },
                  mainCard: {
                    maxWidth: '80rem',
                    width: '100%',
                    backgroundColor: 'white',
                    borderRadius: '12px',
                    boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)',
                    overflow: 'hidden'
                  },
                  header: {
                    backgroundColor: '#4f46e5',
                    padding: '24px',
                    color: 'white'
                  },
                  emojiContainer: {
                    display: 'flex',
                    justifyContent: 'center',
                    marginBottom: '16px'
                  },
                  emoji: {
                    fontSize: '36px'
                  },
                  title: {
                    fontSize: '30px',
                    fontWeight: 'bold',
                    marginBottom: '8px'
                  },
                  subtitle: {
                    color: '#c7d2fe'
                  },
                  content: {
                    padding: '32px'
                  },
                  section: {
                    marginBottom: '32px'
                  },
                  sectionTitle: {
                    fontSize: '24px',
                    fontWeight: '600',
                    color: '#1f2937',
                    marginBottom: '16px'
                  },
                  paragraph: {
                    color: '#4b5563',
                    marginBottom: '16px'
                  },
                  gridContainer: {
                    display: 'grid',
                    gridTemplateColumns: '1fr',
                    gap: '24px',
                    marginBottom: '32px'
                  },
                  featureCardBlue: {
                    backgroundColor: '#eff6ff',
                    borderRadius: '8px',
                    padding: '20px',
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    textAlign: 'center'
                  },
                  featureCardIndigo: {
                    backgroundColor: '#eef2ff',
                    borderRadius: '8px',
                    padding: '20px',
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    textAlign: 'center'
                  },
                  featureEmoji: {
                    fontSize: '36px',
                    marginBottom: '12px'
                  },
                  featureTitle: {
                    fontWeight: '600',
                    fontSize: '20px',
                    marginBottom: '8px',
                    color: '#1f2937'
                  },
                  featureText: {
                    color: '#4b5563'
                  },
                  structureCard: {
                    backgroundColor: '#f9fafb',
                    borderRadius: '8px',
                    padding: '20px'
                  },
                  structureGrid: {
                    display: 'grid',
                    gridTemplateColumns: '1fr',
                    gap: '24px'
                  },
                  structureSubtitle: {
                    fontWeight: '500',
                    fontSize: '16px',
                    marginBottom: '12px',
                    color: '#1f2937'
                  },
                  structureList: {
                    listStyle: 'none',
                    padding: 0,
                    margin: 0,
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '8px',
                    color: '#4b5563',
                    fontSize: '14px'
                  },
                  structureListItem: {
                    display: 'flex',
                    alignItems: 'flex-start'
                  },
                  structureHighlight: {
                    fontWeight: '600',
                    marginRight: '8px',
                    color: '#4f46e5'
                  },
                  commandCard: {
                    backgroundColor: '#ecfdf5',
                    borderRadius: '8px',
                    padding: '20px'
                  },
                  bestPracticesCard: {
                    backgroundColor: '#fffbeb',
                    borderRadius: '8px',
                    padding: '20px'
                  },
                  cardTitle: {
                    fontWeight: '500',
                    fontSize: '16px',
                    marginBottom: '12px',
                    color: '#1f2937'
                  },
                  codeBlock: {
                    backgroundColor: '#1f2937',
                    borderRadius: '6px',
                    padding: '12px',
                    color: 'white',
                    fontFamily: 'monospace',
                    fontSize: '12px',
                    overflowX: 'auto'
                  },
                  codeLine: {
                    marginBottom: '4px'
                  },
                  codeCommand: {
                    color: '#86efac',
                    marginBottom: '12px'
                  },
                  bestPracticesList: {
                    listStyle: 'none',
                    padding: 0,
                    margin: 0,
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '8px',
                    color: '#4b5563',
                    fontSize: '14px'
                  },
                  nextStepsCard: {
                    backgroundColor: '#f9fafb',
                    borderRadius: '8px',
                    padding: '20px',
                    marginBottom: '32px'
                  },
                  nextStepsList: {
                    textAlign: 'left',
                    listStyleType: 'disc',
                    paddingLeft: '20px',
                    color: '#4b5563',
                    display: 'flex',
                    flexDirection: 'column',
                    gap: '8px'
                  },
                  codeInline: {
                    backgroundColor: '#e5e7eb',
                    padding: '2px 8px',
                    borderRadius: '4px',
                    fontFamily: 'monospace',
                    fontSize: '14px'
                  },
                  buttonContainer: {
                    display: 'flex',
                    justifyContent: 'center',
                    gap: '16px'
                  },
                  buttonPrimary: {
                    backgroundColor: '#1f2937',
                    color: 'white',
                    padding: '8px 20px',
                    borderRadius: '8px',
                    textDecoration: 'none',
                    transition: 'background-color 0.2s'
                  },
                  buttonSecondary: {
                    backgroundColor: '#4f46e5',
                    color: 'white',
                    padding: '8px 20px',
                    borderRadius: '8px',
                    textDecoration: 'none',
                    transition: 'background-color 0.2s'
                  },
                  footer: {
                    backgroundColor: '#f3f4f6',
                    padding: '16px',
                    textAlign: 'center',
                    color: '#4b5563',
                    fontSize: '14px'
                  }
                };
                
                export default App;
        """;
    }

    public String getButtonComponentTemplate(ProjectRequest request) {
        if (request.getLanguage().equals("typescript")) {
            return """
                import React from 'react';
                
                interface ButtonProps {
                  children: React.ReactNode;
                  variant?: 'primary' | 'secondary' | 'outline';
                  onClick?: () => void;
                  disabled?: boolean;
                }
                
                const Button: React.FC<ButtonProps> = ({
                  children,
                  variant = 'primary',
                  onClick,
                  disabled = false
                }) => {
                  const getButtonClasses = () => {
                    const baseClasses = 'px-4 py-2 rounded font-medium focus:outline-none transition-colors';
                    
                    switch (variant) {
                      case 'primary':
                        return `${baseClasses} bg-blue-600 text-white hover:bg-blue-700`;
                      case 'secondary':
                        return `${baseClasses} bg-gray-600 text-white hover:bg-gray-700`;
                      case 'outline':
                        return `${baseClasses} border border-blue-600 text-blue-600 hover:bg-blue-50`;
                      default:
                        return baseClasses;
                    }
                  };
                
                  return (
                    <button
                      className={getButtonClasses()}
                      onClick={onClick}
                      disabled={disabled}
                    >
                      {children}
                    </button>
                  );
                };
                
                export default Button;
                """;
        } else {
            return """
                import React from 'react';
                
                const Button = ({
                  children,
                  variant = 'primary',
                  onClick,
                  disabled = false
                }) => {
                  const getButtonClasses = () => {
                    const baseClasses = 'px-4 py-2 rounded font-medium focus:outline-none transition-colors';
                    
                    switch (variant) {
                      case 'primary':
                        return `${baseClasses} bg-blue-600 text-white hover:bg-blue-700`;
                      case 'secondary':
                        return `${baseClasses} bg-gray-600 text-white hover:bg-gray-700`;
                      case 'outline':
                        return `${baseClasses} border border-blue-600 text-blue-600 hover:bg-blue-50`;
                      default:
                        return baseClasses;
                    }
                  };
                
                  return (
                    <button
                      className={getButtonClasses()}
                      onClick={onClick}
                      disabled={disabled}
                    >
                      {children}
                    </button>
                  );
                };
                
                export default Button;
                """;
        }
    }

    public String getLayoutComponentTemplate(ProjectRequest request) {
        if (request.getLanguage().equals("typescript")) {
            return """
                import React from 'react';
                
                interface LayoutProps {
                  children: React.ReactNode;
                  header?: React.ReactNode;
                  footer?: React.ReactNode;
                }
                
                const Layout: React.FC<LayoutProps> = ({ children, header, footer }) => {
                  return (
                    <div className="min-h-screen flex flex-col">
                      {header && (
                        <header className="bg-white shadow-sm">
                          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
                            {header}
                          </div>
                        </header>
                      )}
                
                      <main className="flex-grow">
                        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
                          {children}
                        </div>
                      </main>
                
                      {footer && (
                        <footer className="bg-gray-50">
                          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
                            {footer}
                          </div>
                        </footer>
                      )}
                    </div>
                  );
                };
                
                export default Layout;
                """;
        } else {
            return """
                import React from 'react';
                
                const Layout = ({ children, header, footer }) => {
                  return (
                    <div className="min-h-screen flex flex-col">
                      {header && (
                        <header className="bg-white shadow-sm">
                          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
                            {header}
                          </div>
                        </header>
                      )}
                
                      <main className="flex-grow">
                        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-6">
                          {children}
                        </div>
                      </main>
                
                      {footer && (
                        <footer className="bg-gray-50">
                          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
                            {footer}
                          </div>
                        </footer>
                      )}
                    </div>
                  );
                };
                
                export default Layout;
                """;
        }
    }

    public String getIndexCssTemplate() {
        return """
            :root {
              font-family: Inter, system-ui, Avenir, Helvetica, Arial, sans-serif;
              line-height: 1.5;
              font-weight: 400;
            }
            
            body {
              margin: 0;
              min-width: 320px;
              min-height: 100vh;
            }
            """;
    }

    public String getApiClientTemplate(ProjectRequest request) {
        String ext = request.getLanguage().equals("typescript") ? ".ts" : ".js";
        return """
            import axios from 'axios';
            
            const apiClient = axios.create({
              baseURL: import.meta.env.VITE_API_URL,
              headers: {
                'Content-Type': 'application/json'
              }
            });
            
            export default apiClient;
            """;
    }

    public String getEnglishLanguageTemplate() {
        return """
            {
              "common": {
                "welcome": "Welcome",
                "loading": "Loading...",
                "error": "An error occurred"
              },
              "auth": {
                "login": "Login",
                "logout": "Logout",
                "email": "Email",
                "password": "Password"
              }
            }
            """;
    }

    public String getFrenchLanguageTemplate() {
        return """
            {
              "common": {
                "welcome": "Bienvenue",
                "loading": "Chargement...",
                "error": "Une erreur est survenue"
              },
              "auth": {
                "login": "Connexion",
                "logout": "Déconnexion",
                "email": "Email",
                "password": "Mot de passe"
              }
            }
            """;
    }

    public String getTenantConfigTemplate(ProjectRequest request) {
        if (request.getLanguage().equals("typescript")) {
            return """
                interface TenantTheme {
                  primary: string;
                  secondary: string;
                  accent?: string;
                }
                
                interface Tenant {
                  name: string;
                  theme: TenantTheme;
                  logo?: string;
                  features?: string[];
                }
                
                interface TenantConfiguration {
                  defaultTenant: string;
                  tenants: Record<string, Tenant>;
                }
                
                export const tenantConfig: TenantConfiguration = {
                  defaultTenant: 'default',
                  tenants: {
                    default: {
                      name: 'Default Tenant',
                      theme: {
                        primary: '#2563eb',
                        secondary: '#374151'
                      }
                    }
                  }
                };
                """;
        } else {
            return """
                export const tenantConfig = {
                  defaultTenant: 'default',
                  tenants: {
                    default: {
                      name: 'Default Tenant',
                      theme: {
                        primary: '#2563eb',
                        secondary: '#374151'
                      }
                    }
                  }
                };
                """;
        }
    }

    public String getReadmeTemplate(ProjectRequest request) {
        return String.format("""
            # %s
            
            %s
            
            ## Getting Started
            
            1. Install dependencies:
               ```bash
               npm install
               ```
            
            2. Start the development server:
               ```bash
               npm run dev
               ```
            
            3. Build for production:
               ```bash
               npm run build
               ```
            
            ## Features
            
            - %s
            - Built with Vite
            %s
            %s
            %s
            
            ## Project Structure
            
            ```
            src/
            ├── assets/
            │   ├── images/
            │   └── css/
            ├── pages/
            │   └── components/
            │       ├── button.tsx
            │       └── layout.tsx
            ├── config/
            │   ├── environment/
            │   └── tenantConfig/
            │       └── tenantConfig.ts
            ├── routes/
            └── packages/
                └── core/
                    └── feat-login/
                        ├── components/
                        ├── core/
                        └── web/
            ```
            """,
                request.getProjectName(),
                request.getDescription(),
                request.getLanguage().equals("typescript") ? "TypeScript support" : "JavaScript",
                request.getFeatures().isRouting() ? "- React Router for navigation" : "",
                request.getFeatures().isI18n() ? "- Internationalization support" : "",
                request.getFeatures().isApiClient() ? "- Axios for API communication" : ""
        );
    }

    public String getNavigationTemplate(ProjectRequest request) {
        return """
            import { useNavigate } from 'react-router-dom';

            const useNavigation = () => {
              const navigate = useNavigate();

              return {
                goToHome: () => navigate('/'),
                goToLogin: () => navigate('/login'),
              };
            };

            export default useNavigation;
            """;
    }

    public String getRoutesTemplate(ProjectRequest request) {
        return """
            import React from 'react';
            import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
            import App from '../App';

            const AppRoutes = () => (
              <Router>
                <Routes>
                  <Route path="/" element={<App />} />
                </Routes>
              </Router>
            );

            export default AppRoutes;
            """;
    }

    public String getEnvTemplate(ProjectRequest request) {
        return """
            export const environment = {
              apiUrl: import.meta.env.VITE_API_URL,
              environmentName: 'development'
            };
            """;
    }

    public String getUtilsTemplate(ProjectRequest request) {
        return """
            export const formatDate = (date) => {
              return new Date(date).toLocaleDateString();
            };

            export const isEmpty = (value) => {
              return value == null || value.length === 0;
            };
            """;
    }
}