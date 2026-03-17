import { useState } from "react";
import ResumeUpload from "./components/ResumeUpload";
import ParsedResumeCard from "./components/ParsedResumeCard";
import MatchResults from "./components/MatchResults";
import AddJd from "./components/AddJd";

export default function App() {
  const [activeTab, setActiveTab] = useState("resume");
  const [parsedResume, setParsedResume] = useState(null);

  const tabs = [
    { key: "resume", label: "Upload Resume" },
    { key: "jd", label: "Add Job Description" },
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-50 via-blue-50 to-indigo-50">
      {/* Header */}
      <header className="bg-white/80 backdrop-blur-md border-b border-slate-200 sticky top-0 z-10">
        <div className="max-w-5xl mx-auto px-6 py-4 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 bg-gradient-to-br from-blue-600 to-indigo-600 rounded-lg flex items-center justify-center">
              <span className="text-white text-sm font-bold">R</span>
            </div>
            <div>
              <h1 className="text-base font-bold text-slate-800 leading-none">
                Resume Matcher
              </h1>
              <p className="text-xs text-slate-400 mt-0.5">
                Rule-based skill matching engine
              </p>
            </div>
          </div>

          {/* Tabs */}
          <div className="flex bg-slate-100 rounded-lg p-1 gap-1">
            {tabs.map((tab) => (
              <button
                key={tab.key}
                onClick={() => setActiveTab(tab.key)}
                className={`px-4 py-1.5 rounded-md text-sm font-medium transition-all duration-200
                  ${
                    activeTab === tab.key
                      ? "bg-white text-blue-600 shadow-sm"
                      : "text-slate-500 hover:text-slate-700"
                  }`}
              >
                {tab.label}
              </button>
            ))}
          </div>
        </div>
      </header>

      {/* Content */}
      <main className="max-w-5xl mx-auto px-6 py-10">
        {activeTab === "resume" && (
          <div className="space-y-6">
            {!parsedResume && (
              <div className="text-center mb-8">
                <h2 className="text-2xl font-bold text-slate-800">
                  Upload Your Resume
                </h2>
                <p className="text-slate-500 mt-2 text-sm">
                  We'll extract your skills and match them against all available
                  job descriptions
                </p>
              </div>
            )}
            <ResumeUpload onParsed={setParsedResume} />
            {parsedResume && (
              <>
                <ParsedResumeCard resume={parsedResume} />
                <MatchResults resumeId={parsedResume.id} />
              </>
            )}
          </div>
        )}

        {activeTab === "jd" && (
          <div>
            <div className="text-center mb-8">
              <h2 className="text-2xl font-bold text-slate-800">
                Add Job Description
              </h2>
              <p className="text-slate-500 mt-2 text-sm">
                Paste a job description to add it to the matching pool
              </p>
            </div>
            <AddJd />
          </div>
        )}
      </main>
    </div>
  );
}
