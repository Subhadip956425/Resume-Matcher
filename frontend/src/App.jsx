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
    { key: "jd", label: "Add Job Descriptions" },
  ];

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white border-b border-gray-200 px-6 py-4">
        <h1 className="text-xl font-bold text-gray-800">Resume Matcher</h1>
        <p className="text-xs text-gray-400 mt-0.5">
          Rule-based resume parsing and job matching
        </p>
      </header>

      {/* Tabs */}
      <div className="flex gap-1 px-6 pt-4 border-b border-gray-200 bg-white">
        {tabs.map((tab) => (
          <button
            key={tab.key}
            onClick={() => setActiveTab(tab.key)}
            className={`px-4 py-2 text-sm font-medium border-b-2 transition-colors
              ${
                activeTab === tab.key
                  ? "border-blue-500 text-blue-600"
                  : "border-transparent text-gray-500 hover:text-gray-700"
              }`}
          >
            {tab.label}
          </button>
        ))}
      </div>

      {/* Content */}
      <main className="px-6 py-8 max-w-4xl mx-auto">
        {activeTab === "resume" && (
          <div>
            <ResumeUpload onParsed={setParsedResume} />

            {parsedResume && (
              <>
                <ParsedResumeCard resume={parsedResume} />
                <MatchResults resumeId={parsedResume.id} />
              </>
            )}
          </div>
        )}

        {activeTab === "jd" && <AddJd />}
      </main>
    </div>
  );
}
