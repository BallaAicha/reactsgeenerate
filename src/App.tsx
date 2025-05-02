import React, { useState } from 'react';
import { FileText, Code, Download, Check, Loader2 } from 'lucide-react';
import ProjectForm from './components/ProjectForm';
import ProjectPreview from './components/ProjectPreview';
import Header from './components/Header';
import Footer from './components/Footer';
import { ProjectConfig } from './types';
import './App.css';

// Type pour le statut de la requête
type RequestStatus = 'idle' | 'pending' | 'success' | 'error';

function App() {
  const [step, setStep] = useState<number>(1);
  const [requestStatus, setRequestStatus] = useState<RequestStatus>('idle');
  const [projectConfig, setProjectConfig] = useState<ProjectConfig>({
    projectName: '',
    description: '',
    version: '0.1.0',
    language: 'typescript',
    features: {
      routing: true,
      i18n: true,
      apiClient: true,
      tenantConfig: false
    }
  });

  const handleProjectConfigChange = (config: ProjectConfig) => {
    setProjectConfig(config);
  };

  const handleNextStep = () => {
    setStep(step + 1);
  };

  const handlePreviousStep = () => {
    setStep(step - 1);
  };

  // Fonction pour télécharger le projet
  const handleDownloadProject = () => {
    // Mettre à jour immédiatement le statut de la requête
    setRequestStatus('pending');

    // Préparation de la requête selon le format attendu par le backend
    const projectRequest = {
      projectName: projectConfig.projectName,
      description: projectConfig.description,
      version: projectConfig.version,
      language: projectConfig.language,
      features: projectConfig.features
    };

    // Appel à l'API pour générer et télécharger le projet
    fetch('http://localhost:8080/api/projects/generate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(projectRequest)
    })
        .then(response => {
          if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
          }
          return response.blob();
        })
        .then(blob => {
          // Création d'un lien de téléchargement pour le fichier
          const downloadUrl = window.URL.createObjectURL(blob);
          const link = document.createElement('a');
          link.href = downloadUrl;
          link.download = `${projectConfig.projectName}-project.zip`;

          // Déclenchement du téléchargement
          document.body.appendChild(link);
          link.click();
          document.body.removeChild(link);

          // Nettoyage de l'URL après utilisation
          window.URL.revokeObjectURL(downloadUrl);

          // Mettre à jour le statut à "success"
          setRequestStatus('success');

          // Affiche un message de succès
          alert("Votre projet a été créé avec succès !");
        })
        .catch(error => {
          console.error('Failed to download project:', error);

          // Mettre à jour le statut à "error"
          setRequestStatus('error');

          alert('Failed to download project. Please try again later.');
        });
  };

  // Vérifier si la requête est en cours
  const isPending = requestStatus === 'pending';

  return (
      <div className="min-h-screen flex flex-col bg-gray-50">
        <Header />

        <main className="flex-grow container mx-auto px-4 py-8">
          <div className="max-w-4xl mx-auto">
            {/* Step Indicator */}
            <div className="mb-8">
              <div className="flex items-center justify-between">
                <div className={`flex items-center ${step >= 1 ? 'text-blue-600' : 'text-gray-400'}`}>
                  <div className={`flex items-center justify-center w-8 h-8 rounded-full ${step >= 1 ? 'bg-blue-100' : 'bg-gray-200'}`}>
                    <FileText size={16} className={step >= 1 ? 'text-blue-600' : 'text-gray-400'} />
                  </div>
                  <span className="ml-2 text-sm font-medium">Configuration</span>
                </div>
                <div className={`flex-grow border-t mx-4 ${step >= 2 ? 'border-blue-600' : 'border-gray-300'}`}></div>
                <div className={`flex items-center ${step >= 2 ? 'text-blue-600' : 'text-gray-400'}`}>
                  <div className={`flex items-center justify-center w-8 h-8 rounded-full ${step >= 2 ? 'bg-blue-100' : 'bg-gray-200'}`}>
                    <Code size={16} className={step >= 2 ? 'text-blue-600' : 'text-gray-400'} />
                  </div>
                  <span className="ml-2 text-sm font-medium">Preview</span>
                </div>
                <div className={`flex-grow border-t mx-4 ${step >= 3 ? 'border-blue-600' : 'border-gray-300'}`}></div>
                <div className={`flex items-center ${step >= 3 ? 'text-blue-600' : 'text-gray-400'}`}>
                  <div className={`flex items-center justify-center w-8 h-8 rounded-full ${step >= 3 ? 'bg-blue-100' : 'bg-gray-200'}`}>
                    <Download size={16} className={step >= 3 ? 'text-blue-600' : 'text-gray-400'} />
                  </div>
                  <span className="ml-2 text-sm font-medium">Generate</span>
                </div>
              </div>
            </div>

            {/* Step Content */}
            <div className="bg-white rounded-lg shadow-md p-6">
              {step === 1 && (
                  <ProjectForm
                      projectConfig={projectConfig}
                      onConfigChange={handleProjectConfigChange}
                      onNext={handleNextStep}
                  />
              )}

              {step === 2 && (
                  <ProjectPreview
                      projectConfig={projectConfig}
                      onBack={handlePreviousStep}
                      onNext={handleNextStep}
                  />
              )}

              {step === 3 && (
                  <div className="space-y-6">
                    <div className="text-center py-8">
                      <div className="inline-flex items-center justify-center h-16 w-16 rounded-full bg-green-100 mb-4">
                        <Check size={32} className="text-green-600" />
                      </div>
                      <h2 className="text-2xl font-bold text-gray-800 mb-2">Project Generated Successfully!</h2>
                      <p className="text-gray-600 mb-6">Your project is ready to be downloaded.</p>

                      <div className="bg-gray-100 rounded-lg p-4 text-left mb-6">
                        <p className="text-sm font-mono mb-2">To use this project:</p>
                        <ol className="list-decimal list-inside space-y-2 text-sm font-mono">
                          <li>Extract the downloaded ZIP file</li>
                          <li>Navigate to the project directory: <code className="bg-gray-200 px-1 rounded">cd {projectConfig.projectName}</code></li>
                          <li>Install dependencies: <code className="bg-gray-200 px-1 rounded">npm install</code></li>
                          <li>Start the development server: <code className="bg-gray-200 px-1 rounded">npm run dev</code></li>
                        </ol>
                      </div>

                      <div className="flex flex-col sm:flex-row justify-center space-y-3 sm:space-y-0 sm:space-x-3">
                        <button
                            className="inline-flex items-center justify-center px-4 py-2 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-70"
                            onClick={handleDownloadProject}
                            disabled={isPending}
                        >
                          {isPending ? (
                              <>
                                <Loader2 size={16} className="mr-2 animate-spin" />
                                Generating Project...
                              </>
                          ) : (
                              <>
                                <Download size={16} className="mr-2" />
                                Download Project
                              </>
                          )}
                        </button>
                        <button
                            className="inline-flex items-center justify-center px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 disabled:opacity-70"
                            onClick={handlePreviousStep}
                            disabled={isPending}
                        >
                          Back to Preview
                        </button>
                      </div>

                      {isPending && (
                          <div className="mt-6 p-4 bg-blue-50 text-blue-700 rounded-lg">
                            <p className="font-medium">Votre projet est en cours de préparation</p>
                            <p className="text-sm mt-1">Cela peut prendre quelques minutes pendant que nous chargeons toutes les dépendances nécessaires. Veuillez patienter...</p>
                          </div>
                      )}
                    </div>
                  </div>
              )}
            </div>
          </div>
        </main>

        <Footer />
      </div>
  );
}

export default App;