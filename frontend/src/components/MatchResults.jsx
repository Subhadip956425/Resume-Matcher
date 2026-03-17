import { useState } from "react";
import api from "../api/axios";

export default function MatchResults({ resumeId }) {
  const [results, setResults] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const runMatch = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await api.post(`/match/${resumeId}`);
      setResults(response.data);
    } catch (err) {
      setError(
        "Matching failed. Make sure you have added Job Descriptions first.",
      );
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="w-full max-w-4xl mx-auto mt-8">
      <button
        onClick={runMatch}
        disabled={loading}
        className="w-full bg-blue-600 hover:bg-blue-700 disabled:bg-blue-300 text-white font-semibold py-3 rounded-xl transition-colors"
      >
        {loading ? "Running Match..." : "Match Against All Job Descriptions"}
      </button>

      {error && (
        <p className="mt-3 text-red-500 text-sm text-center">{error}</p>
      )}

      {results && (
        <div className="mt-6 space-y-5">
          <p className="text-gray-500 text-sm text-center">
            {results.matchingJobs.length} JDs matched — sorted by score
          </p>

          {results.matchingJobs.map((job) => (
            <JobMatchCard key={job.jobId} job={job} />
          ))}
        </div>
      )}
    </div>
  );
}

function JobMatchCard({ job }) {
  const [expanded, setExpanded] = useState(false);

  const scoreColor =
    job.matchingScore >= 70
      ? "text-green-600"
      : job.matchingScore >= 40
        ? "text-yellow-600"
        : "text-red-500";

  const barColor =
    job.matchingScore >= 70
      ? "bg-green-500"
      : job.matchingScore >= 40
        ? "bg-yellow-400"
        : "bg-red-400";

  return (
    <div className="bg-white border border-gray-200 rounded-xl p-5 shadow-sm">
      {/* Header row */}
      <div className="flex items-center justify-between">
        <div>
          <h3 className="text-base font-semibold text-gray-800">{job.role}</h3>
          {job.salary && (
            <p className="text-xs text-gray-400 mt-0.5">Salary: {job.salary}</p>
          )}
        </div>
        <div className="text-right">
          <span className={`text-2xl font-bold ${scoreColor}`}>
            {job.matchingScore}%
          </span>
          <p className="text-xs text-gray-400">
            {job.matchedCount}/{job.totalJdSkills} skills
          </p>
        </div>
      </div>

      {/* Score bar */}
      <div className="mt-3 w-full bg-gray-100 rounded-full h-2">
        <div
          className={`${barColor} h-2 rounded-full transition-all duration-500`}
          style={{ width: `${job.matchingScore}%` }}
        />
      </div>

      {/* About role */}
      {job.aboutRole && (
        <p className="mt-3 text-xs text-gray-500 line-clamp-2">
          {job.aboutRole}
        </p>
      )}

      {/* Toggle skill analysis */}
      <button
        onClick={() => setExpanded(!expanded)}
        className="mt-3 text-xs text-blue-500 hover:underline"
      >
        {expanded ? "Hide skill breakdown" : "Show skill breakdown"}
      </button>

      {expanded && (
        <div className="mt-3 flex flex-wrap gap-2">
          {job.skillsAnalysis.map((s) => (
            <span
              key={s.skill}
              className={`text-xs font-medium px-2.5 py-1 rounded-full
                ${
                  s.presentInResume
                    ? "bg-green-100 text-green-700"
                    : "bg-red-100 text-red-500"
                }`}
            >
              {s.presentInResume ? "✓" : "✗"} {s.skill}
            </span>
          ))}
        </div>
      )}
    </div>
  );
}
