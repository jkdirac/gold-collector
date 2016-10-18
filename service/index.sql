CREATE INDEX IF NOT EXISTS hf_nav_merge_code_date_idx ON hf_nav_merge(code, nav_date);
CREATE INDEX IF NOT EXISTS hf_nav_merge_date_idx ON hf_nav_merge(nav_date);

CREATE INDEX IF NOT EXISTS hf_evaluation_merge_code_idx ON hf_evaluation_merge(code);
CREATE INDEX IF NOT EXISTS hf_evaluation_merge_code_date_idx ON hf_evaluation_merge(code, trade_date);
CREATE INDEX IF NOT EXISTS hf_evaluation_merge_code_timescale_idx ON hf_evaluation_merge(code, time_scale);
CREATE INDEX IF NOT EXISTS hf_evaluation_merge_time_code_scale_idx ON hf_evaluation_merge(code, time_scale, trade_date);

CREATE INDEX IF NOT EXISTS hf_evaluation_bm_merge_code_idx ON hf_evaluation_bm_merge(code);
CREATE INDEX IF NOT EXISTS hf_evaluation_bm_merge_code_date_idx ON hf_evaluation_bm_merge(code, trade_date);
CREATE INDEX IF NOT EXISTS hf_evaluation_bm_merge_time_code_date_scale_idx ON hf_evaluation_bm_merge(code, trade_date, time_scale);

CREATE INDEX IF NOT EXISTS hf_history_perform_merge_code_idx ON hf_history_perform_merge(code);
CREATE INDEX IF NOT EXISTS hf_history_perform_merge_trade_date_idx ON hf_history_perform_merge(code, trade_date);
CREATE INDEX IF NOT EXISTS hf_history_perform_merge_code_date_scale_idx ON hf_history_perform_merge(code, trade_date, time_scale);

CREATE INDEX IF NOT EXISTS hf_return_merge_code_idx ON hf_return_merge(code);
CREATE INDEX IF NOT EXISTS hf_return_merge_trade_date_idx ON hf_return_merge(trade_date);

CREATE INDEX IF NOT EXISTS hf_homo_code_merge_type_idx ON hf_homo_code_merge(type2);

CREATE INDEX IF NOT EXISTS hf_product_merge_uid_idx ON hf_product_merge (uid);
CREATE INDEX IF NOT EXISTS hf_product_merge_registration_number_idx ON hf_product_merge (fund_registration_number);
CREATE INDEX IF NOT EXISTS hf_product_merge_legal_name_idx ON hf_product_merge (legal_name);


CREATE INDEX IF NOT EXISTS hf_up_down_perform_merge_code_idx ON hf_up_down_perform_merge (code);
CREATE INDEX IF NOT EXISTS hf_up_down_perform_merge_trade_date_idx ON hf_up_down_perform_merge (trade_date);

CREATE INDEX IF NOT EXISTS hf_robust_fit_merge_code_idx ON hf_robust_fit_merge(code);
