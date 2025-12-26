"use client";


import { useState } from "react";
import { useRouter } from "next/navigation";
import AuthShell from "@/components/AuthShell";
import { Card, CardBody } from "@/components/Card";
import { api, setToken } from "@/lib/api";
import type { AuthResponse } from "@/lib/types";

export default function RegisterPage() {
    const router = useRouter();


    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");


    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState(false);

    async function onSubmit(e: React.FormEvent) {
        e.preventDefault();
        setError(null);
        setLoading(true);

        try {
            const res = await api<AuthResponse>("/api/auth/register", {
                method: "POST",
                body: JSON.stringify({ email, password }),
            });


            setToken(res.accessToken);
            router.push("/dashboard");
        } catch (err: unknown) {
            const message = err instanceof Error ? err.message : "Register failed";
            setError(message);
        } finally {
            setLoading(false);
        }
    }

    return (
        <AuthShell>
            <Card>
                <CardBody>
                    <div className="mb-6">
                        <h1 className="text-2xl font-semibold tracking-tight">Create account</h1>
                        <p className="mt-1 text-sm text-slate-300">
                            Start logging workouts and tracking progress.
                        </p>
                    </div>

                    {error && (
                        <div className="mb-4 rounded-xl border border-red-500/30 bg-red-500/10 px-4 py-3 text-sm text-red-200">
                            {error}
                        </div>
                    )}

                    <form onSubmit={onSubmit} className="space-y-4">
                        <div>
                            <label className="text-sm text-slate-200">Email</label>
                            <input
                                className="
                                  mt-1 w-full rounded-xl border border-white/10 bg-white/5 px-3 py-2.5
                                  text-slate-100 placeholder:text-slate-400 outline-none
                                  focus:border-indigo-400/40 focus:ring-4 focus:ring-indigo-500/20
                                "
                                type="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                autoComplete="email"
                                placeholder="you@example.com"
                                required
                            />
                        </div>

                        <div>
                            <label className="text-sm text-slate-200">Password</label>
                            <input
                                className="
                                      mt-1 w-full rounded-xl border border-white/10 bg-white/5 px-3 py-2.5
                                      text-slate-100 placeholder:text-slate-400 outline-none
                                      focus:border-indigo-400/40 focus:ring-4 focus:ring-indigo-500/20
                                    "
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                autoComplete="new-password"
                                placeholder="Minimum 8 characters"
                                required
                            />

                        </div>

                        <button
                            disabled={loading}
                            className="
                                w-full rounded-xl bg-indigo-500 px-4 py-2.5
                                text-sm font-semibold text-white
                                shadow-lg shadow-indigo-500/20
                                transition hover:bg-indigo-400
                                disabled:opacity-60 disabled:cursor-not-allowed
                                focus:outline-none focus:ring-4 focus:ring-indigo-500/30
                              "
                        >
                            {loading ? "Creating..." : "Create account"}
                        </button>

                        <div className="pt-2 text-center text-sm text-slate-300">
                            Already have an account?{" "}
                            <a className="font-semibold text-indigo-300 hover:text-indigo-200" href="/login">
                                Log in
                            </a>
                        </div>
                    </form>
                </CardBody>
            </Card>


        </AuthShell>
    );
}
