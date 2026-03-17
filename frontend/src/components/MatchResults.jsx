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
      setError("Matching failed. Make sure job descriptions have been added.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="w-full max-w-4xl mx-auto">
      <button
        onClick={runMatch}
        disabled={loading}
        className="w-full max-w-xl mx-auto flex items-center justify-center gap-2 bg-gradient-to-r from-blue-600 to-indigo-600
          hover:from-blue-700 hover:to-indigo-700 disabled:from-blue-300 disabled:to-indigo-300
          text-white font-semibold py-3.5 rounded-2xl transition-all duration-200 shadow-md hover:shadow-lg"
      >
        {loading ? (
          <>
            <svg
              className="w-4 h-4 animate-spin"
              fill="none"
              viewBox="0 0 24 24"
            >
              <circle
                className="opacity-25"
                cx="12"
                cy="12"
                r="10"
                stroke="currentColor"
                strokeWidth="4"
              />
              <path
                className="opacity-75"
                fill="currentColor"
                d="M4 12a8 8 0 018-8v8z"
              />
            </svg>
            Running Match...
          </>
        ) : (
          "Match Against All Job Descriptions"
        )}
      </button>

      {error && (
        <div className="mt-4 flex items-center gap-2 bg-red-50 border border-red-200 rounded-xl px-4 py-3">
          <svg
            className="w-4 h-4 text-red-500 shrink-0"
            fill="currentColor"
            viewBox="0 0 20 20"
          >
            <path
              fillRule="evenodd"
              d="M10 18a8 8 0 100-16 8 8 0 000 16zm-.75-4.75a.75.75 0 001.5 0v-4.5a.75.75 0 00-1.5 0v4.5zm.75-7.5a.75.75 0 100 1.5.75.75 0 000-1.5z"
              clipRule="evenodd"
            />
          </svg>
          <p className="text-red-600 text-sm">{error}</p>
        </div>
      )}

      {results && (
        <div className="mt-8 space-y-4">
          <div className="flex items-center justify-between px-1">
            <p className="text-sm font-semibold text-slate-700">
              {results.matchingJobs.length} Job Descriptions Matched
            </p>
            <div className="flex items-center gap-3 text-xs text-slate-500">
              <span className="flex items-center gap-1">
                <span className="w-2 h-2 rounded-full bg-green-500 inline-block" />{" "}
                Strong (≥70%)
              </span>
              <span className="flex items-center gap-1">
                <span className="w-2 h-2 rounded-full bg-yellow-400 inline-block" />{" "}
                Fair (40–69%)
              </span>
              <span className="flex items-center gap-1">
                <span className="w-2 h-2 rounded-full bg-red-400 inline-block" />{" "}
                Weak (&lt;40%)
              </span>
            </div>
          </div>

          {results.matchingJobs.map((job, index) => (
            <JobMatchCard key={job.jobId} job={job} rank={index + 1} />
          ))}
        </div>
      )}
    </div>
  );
}

function JobMatchCard({ job, rank }) {
  const [expanded, setExpanded] = useState(false);

  const isStrong = job.matchingScore >= 70;
  const isFair = job.matchingScore >= 40 && job.matchingScore < 70;

  const scoreColor = isStrong
    ? "text-green-600"
    : isFair
      ? "text-yellow-600"
      : "text-red-500";
  const barColor = isStrong
    ? "bg-green-500"
    : isFair
      ? "bg-yellow-400"
      : "bg-red-400";
  const badgeBg = isStrong
    ? "bg-green-50 text-green-700 border-green-200"
    : isFair
      ? "bg-yellow-50 text-yellow-700 border-yellow-200"
      : "bg-red-50 text-red-600 border-red-200";
  const badgeLabel = isStrong
    ? "Strong Match"
    : isFair
      ? "Fair Match"
      : "Weak Match";

  return (
    <div className="bg-white border border-slate-200 rounded-2xl overflow-hidden shadow-sm hover:shadow-md transition-shadow duration-200">
      <div className="p-5">
        <div className="flex items-start justify-between gap-4">
          {/* Left: rank + role */}
          <div className="flex items-center gap-3">
            <div className="w-8 h-8 rounded-xl bg-slate-100 flex items-center justify-center shrink-0">
              <span className="text-xs font-bold text-slate-500">#{rank}</span>
            </div>
            <div>
              <h3 className="text-sm font-bold text-slate-800 leading-tight">
                {job.role}
              </h3>
              {job.salary && (
                <p className="text-xs text-slate-400 mt-0.5">{job.salary}</p>
              )}
            </div>
          </div>

          <div className="text-right shrink-0">
            <p className={`text-3xl font-black ${scoreColor} leading-none`}>
              {job.matchingScore}%
            </p>
            <p className="text-xs text-slate-400 mt-0.5">
              {job.matchedCount}/{job.totalJdSkills} skills
            </p>
          </div>
        </div>

        {/* Score bar */}
        <div className="mt-4 w-full bg-slate-100 rounded-full h-1.5">
          <div
            className={`${barColor} h-1.5 rounded-full transition-all duration-700`}
            style={{ width: `${job.matchingScore}%` }}
          />
        </div>

        <div className="mt-3 flex items-center justify-between gap-3">
          <span
            className={`text-xs font-semibold px-2.5 py-1 rounded-full border ${badgeBg}`}
          >
            {badgeLabel}
          </span>
          {job.aboutRole && (
            <p className="text-xs text-slate-400 line-clamp-1 flex-1 text-right">
              {job.aboutRole}
            </p>
          )}
        </div>

        <button
          onClick={() => setExpanded(!expanded)}
          className="mt-4 flex items-center gap-1 text-xs text-blue-500 hover:text-blue-700 font-medium transition-colors"
        >
          <svg
            className={`w-3.5 h-3.5 transition-transform duration-200 ${expanded ? "rotate-180" : ""}`}
            fill="none"
            stroke="currentColor"
            strokeWidth="2"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              d="M19 9l-7 7-7-7"
            />
          </svg>
          {expanded ? "Hide skill breakdown" : "View skill breakdown"}
        </button>
      </div>

      {expanded && (
        <div className="border-t border-slate-100 bg-slate-50 px-5 py-4">
          <div className="flex flex-wrap gap-2">
            {job.skillsAnalysis.map((s) => (
              <span
                key={s.skill}
                className={`text-xs font-medium px-3 py-1 rounded-full flex items-center gap-1
                  ${
                    s.presentInResume
                      ? "bg-green-100 text-green-700 border border-green-200"
                      : "bg-red-50 text-red-500 border border-red-200"
                  }`}
              >
                <span
                  className={`w-1.5 h-1.5 rounded-full ${s.presentInResume ? "bg-green-500" : "bg-red-400"}`}
                />
                {s.skill}
              </span>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}
