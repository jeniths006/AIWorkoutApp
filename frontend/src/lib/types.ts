export type AuthResponse = {
    access_token: string;
    token_type: string;
    userId: string;
    email: string;
};

export type MeResponse = {
    userId: string;
    email: string;
}